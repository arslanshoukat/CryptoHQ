package com.haroof.data.model

import com.haroof.network.model.DetailedCoinDto

data class DetailedCoin(
  val id: String,
  val name: String,
  val symbol: String,
  val currentPrice: Float,
  val priceChange24h: Float,
  val priceChangePercentage24h: Float,
  val marketCap: Long,
  val marketCapRank: Int,
  val imageUrl: String,
  val sparklineIn7d: List<Float>,
  val low24h: Float,
  val high24h: Float,
  val allTimeLow: Float,
  val allTimeHigh: Float,
) {

  val marketTrend: MarketTrend
    get() = if (priceChangePercentage24h > 0) MarketTrend.UP else if (priceChangePercentage24h < 0) MarketTrend.DOWN else MarketTrend.NEUTRAL
}

fun DetailedCoinDto.toExternalModel(vs_currency: String) = DetailedCoin(
  id = id,
  name = name,
  symbol = symbol,
  currentPrice = market_data.current_price[vs_currency] ?: 0f,
  priceChange24h = market_data.price_change_24h,
  priceChangePercentage24h = market_data.price_change_percentage_24h,
  marketCap = market_data.market_cap[vs_currency] ?: 0L,
  marketCapRank = market_cap_rank,
  imageUrl = image.thumb.orEmpty(),
  sparklineIn7d = emptyList(),
  low24h = market_data.low_24h[vs_currency] ?: 0f,
  high24h = market_data.high_24h[vs_currency] ?: 0f,
  allTimeLow = market_data.atl[vs_currency] ?: 0f,
  allTimeHigh = market_data.ath[vs_currency] ?: 0f,
)