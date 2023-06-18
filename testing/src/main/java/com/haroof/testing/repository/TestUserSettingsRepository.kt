package com.haroof.testing.repository

import com.haroof.data.repository.UserSettingsRepository
import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filterNotNull

class TestUserSettingsRepository : UserSettingsRepository {

  private val _sourceCurrency =
    MutableSharedFlow<String>(replay = 1, onBufferOverflow = DROP_OLDEST)

  private val _targetCurrency =
    MutableSharedFlow<String>(replay = 1, onBufferOverflow = DROP_OLDEST)

  private val _defaultCurrency =
    MutableSharedFlow<Pair<String, String>>(replay = 1, onBufferOverflow = DROP_OLDEST)

  override val sourceCurrency: Flow<String>
    get() = _sourceCurrency.filterNotNull()

  override val targetCurrency: Flow<String>
    get() = _targetCurrency.filterNotNull()

  override val defaultCurrency: Flow<Pair<String, String>>
    get() = _defaultCurrency.filterNotNull()

  override suspend fun updateSourceCurrency(currencyCode: String) {
    _sourceCurrency.tryEmit(currencyCode)
  }

  override suspend fun updateTargetCurrency(currencyCode: String) {
    _targetCurrency.tryEmit(currencyCode)
  }

  override suspend fun updateDefaultCurrency(currencyCode: String, currencyUnit: String) {
    _defaultCurrency.tryEmit(currencyCode to currencyUnit)
  }
}