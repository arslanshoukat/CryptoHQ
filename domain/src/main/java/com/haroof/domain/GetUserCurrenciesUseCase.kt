package com.haroof.domain

import com.haroof.data.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetUserCurrenciesUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository
) {

  operator fun invoke(): Flow<Pair<String, String>> {
    return combine(
      userSettingsRepository.sourceCurrency,
      userSettingsRepository.targetCurrency
    ) { sourceCurrency, targetCurrency ->
      sourceCurrency to targetCurrency
    }
  }
}