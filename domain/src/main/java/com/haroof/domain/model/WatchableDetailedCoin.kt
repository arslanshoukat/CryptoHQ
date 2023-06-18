package com.haroof.domain.model

import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.Links

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
  val currencyUnit: String,
) {

  val marketTrend: MarketTrend
    get() = if (priceChangePercentage24h > 0) MarketTrend.UP else if (priceChangePercentage24h < 0) MarketTrend.DOWN else MarketTrend.NEUTRAL

  val currentPriceString: String
    get() = currencyUnit + currentPrice.toBigDecimal().toPlainString()

  val marketCapString: String
    get() = currencyUnit + marketCap.toBigDecimal().toPlainString()

  val low24hString: String
    get() = currencyUnit + low24h.toBigDecimal().toPlainString()

  val high24hString: String
    get() = currencyUnit + high24h.toBigDecimal().toPlainString()

  val allTimeLowString: String
    get() = currencyUnit + allTimeLow.toBigDecimal().toPlainString()

  val allTimeHighString: String
    get() = currencyUnit + allTimeHigh.toBigDecimal().toPlainString()
}

fun DetailedCoin.toDomainModel(isWatched: Boolean, currencyUnit: String) = WatchableDetailedCoin(
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
  currencyUnit = currencyUnit,
)

fun WatchableDetailedCoin.toDataModel() = DetailedCoin(
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
  links = Links(
    homepage = homepage,
    facebook = facebookLink,
    twitter = twitterLink,
    reddit = redditLink,
  ),
  description = description,
  sparklineIn7d = emptyList(),
)