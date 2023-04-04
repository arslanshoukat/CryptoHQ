package com.haroof.common.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.coroutines.cancellation.CancellationException

sealed interface Result<out T> {

  object Loading : Result<Nothing>

  data class Success<T>(val data: T) : Result<T>

  data class Error(val exception: Throwable? = null) : Result<Nothing>
}

/**
 * N is type of response returned from suspending network block.
 * T is model type we want returned, wrapped inside a Result object.
 * Here, we start off by emitting Loading and then we call passed suspend function to
 * get object of type N. If it succeeds, we use mapper function to map N to T,
 * and then wrap this inside a Success result object. If it fails and we get some exception
 * e.g. HttpException, IOException, we catch this and return Error result with exception.
 * We take special care to propagate CancellationException further down chain to ensure
 * structured concurrency.
 */
fun <T, N> resultFlow(mapper: N.() -> T, block: suspend () -> N): Flow<Result<T>> =
  flow {
    val response = block()
    emit(response)
  }
    .map<N, Result<T>> { Result.Success(it.mapper()) }
    .onStart { emit(Result.Loading) }
    .catch {
      if (it is CancellationException) throw it  //  todo: consider if we need to return a result here
      else emit(Result.Error(it))
    }
    .flowOn(Dispatchers.IO)

fun <T, N> listResultFlow(mapper: N.() -> T, block: suspend () -> List<N>): Flow<Result<List<T>>> =
  flow {
    val response = block()
    emit(response)
  }
    .map<List<N>, Result<List<T>>> { Result.Success(it.map(mapper)) }
    .onStart { emit(Result.Loading) }
    .catch {
      if (it is CancellationException) throw it  //  todo: consider if we need to return a result here
      else emit(Result.Error(it))
    }
    .flowOn(Dispatchers.IO)
