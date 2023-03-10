package com.haroof.common.di

import android.content.Context
import coil.ImageLoader
import coil.util.DebugLogger
import com.haroof.common.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageLoaderModule {

  @Provides
  @Singleton
  fun providesImageLoader(
    @ApplicationContext application: Context,
    okHttpCallFactory: Call.Factory,
  ): ImageLoader = ImageLoader.Builder(application)
    .callFactory(okHttpCallFactory)
    // Show a short crossfade when loading images asynchronously.
    .crossfade(true)
    // Show logs in Android Log for debug build
    .apply {
      if (BuildConfig.DEBUG) logger(DebugLogger())
    }
    .build()
}