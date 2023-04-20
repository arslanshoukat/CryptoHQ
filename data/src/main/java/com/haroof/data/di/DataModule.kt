package com.haroof.data.di

import com.haroof.data.repository.ChartRepository
import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.CurrencyRepository
import com.haroof.data.repository.DefaultChartRepository
import com.haroof.data.repository.DefaultCoinsRepository
import com.haroof.data.repository.DefaultCurrencyRepository
import com.haroof.data.repository.DefaultWatchListRepository
import com.haroof.data.repository.WatchListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

  @Binds
  fun bindsCoinsRepository(coinsRepository: DefaultCoinsRepository): CoinsRepository

  @Binds
  fun bindsWatchListRepository(watchListRepository: DefaultWatchListRepository): WatchListRepository

  @Binds
  fun bindsChartRepository(chartRepository: DefaultChartRepository): ChartRepository

  @Binds
  fun bindsCurrencyRepository(currencyRepository: DefaultCurrencyRepository): CurrencyRepository
}