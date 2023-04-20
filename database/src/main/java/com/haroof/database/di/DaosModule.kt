package com.haroof.database.di

import com.haroof.CryptoHqDatabase
import com.haroof.database.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

  @Provides
  fun providesCurrencyDao(database: CryptoHqDatabase): CurrencyDao = database.currencyDao()
}