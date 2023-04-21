package com.haroof.data.repository

import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

  val sourceCurrency: Flow<String>

  val toCurrency: Flow<String>

  suspend fun updateSourceCurrency(currencyCode: String)

  suspend fun updateToCurrency(currencyCode: String)
}