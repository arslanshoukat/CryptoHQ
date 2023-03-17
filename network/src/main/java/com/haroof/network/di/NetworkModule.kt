package com.haroof.network.di

import com.haroof.network.BuildConfig
import com.haroof.network.NetworkDataSource
import com.haroof.network.retrofit.RetrofitNetworkDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  @Singleton
  fun providesJson(): Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
  }

  @Provides
  @Singleton
  fun providesOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
    .addInterceptor(
      HttpLoggingInterceptor()
        .apply {
          if (BuildConfig.DEBUG) {
            setLevel(HttpLoggingInterceptor.Level.BODY)
          }
        },
    ).build()

  @Provides
  @Singleton
  fun providesNetworkDataSource(
    json: Json,
    okhttpCallFactory: Call.Factory,
  ): NetworkDataSource = Retrofit.Builder()
    .baseUrl("https://api.coingecko.com/api/v3/")
    .callFactory(okhttpCallFactory)
    .addConverterFactory(
      @OptIn(ExperimentalSerializationApi::class)
      json.asConverterFactory("application/json".toMediaType()),
    )
    .build()
    .create(RetrofitNetworkDataSource::class.java)
}
