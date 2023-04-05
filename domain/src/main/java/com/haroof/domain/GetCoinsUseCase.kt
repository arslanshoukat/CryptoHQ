package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.Result.Error
import com.haroof.common.model.Result.Loading
import com.haroof.common.model.Result.Success
import com.haroof.data.repository.CoinsRepository
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
  private val coinsRepository: CoinsRepository
) {

  operator fun invoke(): Flow<Result<List<SimpleCoin>>> {
    return coinsRepository.getCoins().map { result ->
      when (result) {
        Loading -> Loading
        is Error -> Error(result.exception)
        is Success -> Success(result.data.map { it.toExternalModel() })
      }
    }
  }
}