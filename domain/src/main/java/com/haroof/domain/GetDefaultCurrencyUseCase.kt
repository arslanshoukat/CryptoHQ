package com.haroof.domain

import com.haroof.data.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDefaultCurrencyUseCase @Inject constructor(
  private val userSettingsRepository: UserSettingsRepository
) {

  operator fun invoke(): Flow<Pair<String, String>> {
    return userSettingsRepository.defaultCurrency
  }
}