package com.haroof.data.repository

import com.haroof.data.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

  fun getCurrencies(): Flow<List<Currency>>
}