package com.haroof.domain.model

import com.haroof.data.model.DetailedCoin

data class WatchableDetailedCoin(
  val id: String,
  val name: String,
  val symbol: String,
  val currentPrice: Float,
  val priceChange24h: Float,
  val priceChangePercentage24h: Float,
  val priceChangePercentage7d: Float,
  val priceChangePercentage30d: Float,
  val priceChangePercentage1y: Float,
  val marketCap: Long,
  val marketCapRank: Int,
  val imageUrl: String,
  val low24h: Float,
  val high24h: Float,
  val allTimeLow: Float,
  val allTimeHigh: Float,
  val circulatingSupply: Double,
  val maxSupply: Double,
  val totalSupply: Double,
  val description: String,
  val homepage: String,
  val facebookLink: String,
  val twitterLink: String,
  val redditLink: String,
  val isWatched: Boolean,
) {

  val marketTrend: MarketTrend
    get() = if (priceChangePercentage24h > 0) MarketTrend.UP else if (priceChangePercentage24h < 0) MarketTrend.DOWN else MarketTrend.NEUTRAL
}

fun DetailedCoin.toExternalModel(isWatched: Boolean) = WatchableDetailedCoin(
  id = id,
  name = name,
  symbol = symbol,
  currentPrice = currentPrice,
  priceChange24h = priceChange24h,
  priceChangePercentage24h = priceChangePercentage24h,
  priceChangePercentage7d = priceChangePercentage7d,
  priceChangePercentage30d = priceChangePercentage30d,
  priceChangePercentage1y = priceChangePercentage1y,
  marketCap = marketCap,
  marketCapRank = marketCapRank,
  imageUrl = imageUrl,
  low24h = low24h,
  high24h = high24h,
  allTimeLow = allTimeLow,
  allTimeHigh = allTimeHigh,
  circulatingSupply = circulatingSupply,
  maxSupply = maxSupply,
  totalSupply = totalSupply,
  description = description,
  homepage = links.homepage,
  facebookLink = links.facebook,
  twitterLink = links.twitter,
  redditLink = links.reddit,
  isWatched = isWatched,
)