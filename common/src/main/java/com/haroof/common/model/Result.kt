package com.haroof.common.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
 * This extension function is used to convert R type to T wrapped inside a Result object.
 * T is type we are converting from.
 * R is model type we want returned, wrapped inside a Result object.
 * Here, we start off by emitting Loading. Then we use mapper function to map T to R,
 * and then wrap this inside a Success result object. If some exception is thrown
 * e.g. HttpException, IOException, we catch this and return Error result with exception.
 * We take special care to propagate CancellationException further down chain to ensure
 * structured concurrency.
 */
fun <R, T> Flow<List<T>>.asResult(mapper: T.() -> R): Flow<Result<List<R>>> =
  this
    .map<List<T>, Result<List<R>>> {
      Result.Success(it.map(mapper))
    }
    .onStart { emit(Result.Loading) }
    .catch {
      if (it is CancellationException) throw it  //  todo: consider if we need to return a result here
      else emit(Result.Error(it))
    }
    .flowOn(Dispatchers.IO)
