package com.haroof.domain.model

import com.haroof.data.model.Coin

data class SimpleCoin(
  val id: String,
  val name: String,
  val symbol: String,
  val currentPrice: Float,
  val priceChangePercentage24h: Float,
  val marketCapRank: Int,
  val imageUrl: String,
  val sparklineIn7d: List<Float>,
  val currencyUnit: String,
) {

  val marketTrend: MarketTrend
    get() = if (priceChangePercentage24h > 0) MarketTrend.UP else if (priceChangePercentage24h < 0) MarketTrend.DOWN else MarketTrend.NEUTRAL

  val currentPriceString: String
    get() = currencyUnit + currentPrice.toBigDecimal().toPlainString()
}

fun Coin.toDomainModel(currencyUnit: String) = SimpleCoin(
  id = id,
  name = name,
  symbol = symbol,
  currentPrice = currentPrice,
  priceChangePercentage24h = priceChangePercentage24h,
  marketCapRank = marketCapRank,
  imageUrl = imageUrl,
  sparklineIn7d = sparklineIn7d,
  currencyUnit = currencyUnit,
)

fun SimpleCoin.toDataModel() = Coin(
  id = id,
  name = name,
  symbol = symbol,
  currentPrice = currentPrice,
  priceChange24h = 0f,
  priceChangePercentage24h = priceChangePercentage24h,
  marketCap = 0L,
  marketCapRank = marketCapRank,
  imageUrl = imageUrl,
  sparklineIn7d = sparklineIn7d,
)