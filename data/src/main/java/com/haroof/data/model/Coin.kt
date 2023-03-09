package com.haroof.data.model

data class Coin(
  val id: String,
  val name: String,
  val symbol: String,
  val currentPrice: Float,
  val priceChange24h: Float,
  val priceChangePercentage24h: Float,
  val marketCap: Long,
  val marketCapRank: Int,
) {

  val marketTrend: MarketTrend
    get() = if (priceChangePercentage24h > 0) MarketTrend.UP else if (priceChangePercentage24h < 0) MarketTrend.DOWN else MarketTrend.NEUTRAL
}
