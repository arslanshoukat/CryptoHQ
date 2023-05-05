package com.haroof.testing.repository

import com.haroof.data.repository.UserSettingsRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

class TestUserSettingsRepository : UserSettingsRepository {

  private val _sourceCurrency =
    MutableSharedFlow<String>(replay = 1, onBufferOverflow = DROP_OLDEST)

  private val _toCurrency =
    MutableSharedFlow<String>(replay = 1, onBufferOverflow = DROP_OLDEST)

  override val sourceCurrency: Flow<String>
    get() = _sourceCurrency.filterNotNull()

  override val toCurrency: Flow<String>
    get() = _toCurrency.filterNotNull()

  override suspend fun updateSourceCurrency(currencyCode: String) {
    _sourceCurrency.tryEmit(currencyCode)
  }

  override suspend fun updateToCurrency(currencyCode: String) {
    _toCurrency.tryEmit(currencyCode)
  }
}