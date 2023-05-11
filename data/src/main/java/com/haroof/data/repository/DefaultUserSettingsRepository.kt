package com.haroof.data.repository

import com.haroof.datastore.CryptoHqPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultUserSettingsRepository @Inject constructor(
  private val preferencesDataSource: CryptoHqPreferencesDataSource,
) : UserSettingsRepository {

  override val sourceCurrency: Flow<String>
    get() = preferencesDataSource.sourceCurrency

  override val targetCurrency: Flow<String>
    get() = preferencesDataSource.targetCurrency

  override val defaultCurrency: Flow<String>
    get() = preferencesDataSource.defaultCurrency

  override suspend fun updateSourceCurrency(currencyCode: String) {
    preferencesDataSource.updateSourceCurrency(currencyCode)
  }

  override suspend fun updateTargetCurrency(currencyCode: String) {
    preferencesDataSource.updateTargetCurrency(currencyCode)
  }

  override suspend fun updateDefaultCurrency(currencyCode: String) {
    preferencesDataSource.updateDefaultCurrency(currencyCode)
  }
}