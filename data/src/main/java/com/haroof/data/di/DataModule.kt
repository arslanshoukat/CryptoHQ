package com.haroof.data.di

import com.haroof.data.repository.CoinsRepository
import com.haroof.data.repository.fake.FakeCoinsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

  @Binds
  fun bindsCoinsRepository(coinsRepository: FakeCoinsRepository): CoinsRepository
}