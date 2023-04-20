package com.haroof.data.model

import com.haroof.database.model.CurrencyEntity
import com.haroof.network.model.ExchangeRateDto

data class Currency(
  val code: String,
  val name: String,
  val unit: String,
  val ratePerBtc: Double,
  val type: String,
)

fun ExchangeRateDto.toCurrencyEntity(code: String) = CurrencyEntity(
  code = code,
  name = name,
  unit = unit,
  ratePerBtc = value,
  type = type,
)

fun CurrencyEntity.toCurrency() = Currency(
  code = code,
  name = name,
  unit = unit,
  ratePerBtc = ratePerBtc,
  type = type,
)
