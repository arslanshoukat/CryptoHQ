package com.haroof.home.di

import coil.ImageLoader
import com.haroof.common.FakeImageLoader
import com.haroof.common.di.ImageLoaderModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [ImageLoaderModule::class]
)
interface FakeImageLoaderModule {

  @Binds
  @Singleton
  fun bindsImageLoader(imageLoader: FakeImageLoader): ImageLoader
}