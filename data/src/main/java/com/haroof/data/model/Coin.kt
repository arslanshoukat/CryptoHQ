package com.haroof.data.model

import com.haroof.network.model.CoinDto

data class Coin(
  val id: String,
  val name: String,
  val symbol: String,
  val currentPrice: Float,
  val priceChange24h: Float,
  val priceChangePercentage24h: Float,
  val marketCap: Long,
  val marketCapRank: Int,
  val imageUrl: String,
  val sparklineIn7d: List<Float>
)

fun CoinDto.toExternalModel() = Coin(
  id = id,
  name = name,
  symbol = symbol,
  currentPrice = current_price,
  priceChange24h = price_change_24h,
  priceChangePercentage24h = price_change_percentage_24h,
  marketCap = market_cap,
  marketCapRank = market_cap_rank,
  imageUrl = image,
  sparklineIn7d = sparkline_in_7d?.price.orEmpty(),
)