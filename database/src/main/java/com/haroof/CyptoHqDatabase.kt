package com.haroof

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haroof.database.dao.CurrencyDao
import com.haroof.database.model.CurrencyEntity

@Database(
  entities = [CurrencyEntity::class],
  version = 1,
  exportSchema = true
)
abstract class CryptoHqDatabase : RoomDatabase() {

  abstract fun currencyDao(): CurrencyDao
}