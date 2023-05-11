package com.haroof.domain

import com.haroof.data.repository.UserSettingsRepository
import javax.inject.Inject

class UpdateDefaultCurrencyUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository
) {

  suspend operator fun invoke(currencyCode: String) {
    userSettingsRepository.updateDefaultCurrency(currencyCode)
  }
}