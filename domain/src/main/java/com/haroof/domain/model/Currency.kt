package com.haroof.domain.model

data class Currency(
  val alphabeticCode: String,
  val name: String,
  val unit: String,
  val currentValue: Double,
  val ratePerBtc: Double,
  val type: String,
)