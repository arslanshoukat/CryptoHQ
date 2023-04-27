package com.haroof.domain

import com.haroof.data.repository.UserSettingsRepository
import javax.inject.Inject

class UpdateUserSelectedCurrencyUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository
) {

  suspend operator fun invoke(sourceCurrency: Boolean, currencyCode: String) {
    if (sourceCurrency) userSettingsRepository.updateSourceCurrency(currencyCode)
    else userSettingsRepository.updateToCurrency(currencyCode)
  }
}