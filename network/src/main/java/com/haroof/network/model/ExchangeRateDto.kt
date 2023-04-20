package com.haroof.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateDto(
  val name: String,
  val type: String,
  val unit: String,
  val value: Double
)