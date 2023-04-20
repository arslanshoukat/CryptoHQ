package com.haroof.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.haroof.database.model.CurrencyEntity

@Dao
interface CurrencyDao {

  @Query("SELECT * FROM currencies")
  suspend fun getCurrencies(): List<CurrencyEntity>

  @Upsert
  suspend fun upsert(entities: List<CurrencyEntity>)

  @Query("DELETE FROM currencies")
  suspend fun deleteCurrencies()
}