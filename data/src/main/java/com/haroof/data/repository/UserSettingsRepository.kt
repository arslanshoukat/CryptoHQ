package com.haroof.data.repository

import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

  val sourceCurrency: Flow<String>

  val targetCurrency: Flow<String>

  val defaultCurrency: Flow<String>

  suspend fun updateSourceCurrency(currencyCode: String)

  suspend fun updateTargetCurrency(currencyCode: String)

  suspend fun updateDefaultCurrency(currencyCode: String)
}