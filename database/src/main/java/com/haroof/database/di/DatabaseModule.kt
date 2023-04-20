package com.haroof.database.di

import android.content.Context
import androidx.room.Room
import com.haroof.CryptoHqDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun providesCryptoHqDatabase(
    @ApplicationContext context: Context
  ): CryptoHqDatabase = Room.databaseBuilder(
    context,
    CryptoHqDatabase::class.java,
    "cryptohq-database"
  ).build()
}