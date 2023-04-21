package com.haroof.data.repository.fake

import com.haroof.data.repository.UserSettingsRepository
import com.haroof.datastore.CryptoHqPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FakeUserSettingsRepository @Inject constructor(
  private val preferencesDataSource: CryptoHqPreferencesDataSource
) : UserSettingsRepository {

  override val sourceCurrency: Flow<String>
    get() = preferencesDataSource.sourceCurrency

  override val toCurrency: Flow<String>
    get() = preferencesDataSource.toCurrency

  override suspend fun updateSourceCurrency(currencyCode: String) {
    preferencesDataSource.updateSourceCurrency(currencyCode)
  }

  override suspend fun updateToCurrency(currencyCode: String) {
    preferencesDataSource.updateToCurrency(currencyCode)
  }
}