package com.haroof.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.haroof.database.model.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

  @Query("SELECT * FROM currencies")
  fun getCurrencies(): Flow<List<CurrencyEntity>>

  @Upsert
  suspend fun upsert(entities: List<CurrencyEntity>)

  @Query("DELETE FROM currencies")
  suspend fun deleteCurrencies()
}