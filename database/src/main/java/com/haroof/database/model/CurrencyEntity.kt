package com.haroof.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
  @PrimaryKey val code: String,
  val name: String,
  val unit: String,
  val ratePerBtc: Double,
  val type: String,
)