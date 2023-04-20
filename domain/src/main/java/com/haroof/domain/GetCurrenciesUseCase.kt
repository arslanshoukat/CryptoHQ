package com.haroof.domain

import com.haroof.common.model.Result
import com.haroof.common.model.asResult
import com.haroof.data.model.Currency
import com.haroof.data.repository.CurrencyRepository
import com.haroof.domain.model.CurrencyUiModel
import com.haroof.domain.model.toExternalModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
  private val currencyRepository: CurrencyRepository
) {

  operator fun invoke(): Flow<Result<List<CurrencyUiModel>>> {
    return currencyRepository.getCurrencies().asResult(mapper = Currency::toExternalModel)
  }
}