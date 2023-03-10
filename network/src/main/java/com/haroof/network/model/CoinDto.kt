package com.haroof.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
  val id: String,
  val name: String,
  val symbol: String,
  val current_price: Float,
  val price_change_24h: Float,
  val price_change_percentage_24h: Float,
  val market_cap: Long,
  val market_cap_rank: Int,
  val image: String,
)
