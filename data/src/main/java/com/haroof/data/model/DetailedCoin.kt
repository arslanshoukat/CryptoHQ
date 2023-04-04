package com.haroof.data.model

import com.haroof.network.model.DetailedCoinDto
import com.haroof.network.model.LinksDto

data class DetailedCoin(
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
  val sparklineIn7d: List<Float>,
  val low24h: Float,
  val high24h: Float,
  val allTimeLow: Float,
  val allTimeHigh: Float,
  val circulatingSupply: Double,
  val maxSupply: Double,
  val totalSupply: Double,
  val links: Links,
  val description: String,
)

data class Links(
  val homepage: String,
  val facebook: String,
  val twitter: String,
  val reddit: String,
)

fun DetailedCoinDto.toExternalModel(vs_currency: String) = DetailedCoin(
  id = id,
  name = name,
  symbol = symbol,
  currentPrice = market_data.current_price[vs_currency] ?: 0f,
  priceChange24h = market_data.price_change_24h_in_currency[vs_currency] ?: 0f,
  priceChangePercentage24h = market_data.price_change_percentage_24h_in_currency[vs_currency] ?: 0f,
  priceChangePercentage7d = market_data.price_change_percentage_7d_in_currency[vs_currency] ?: 0f,
  priceChangePercentage30d = market_data.price_change_percentage_30d_in_currency[vs_currency] ?: 0f,
  priceChangePercentage1y = market_data.price_change_percentage_1y_in_currency[vs_currency] ?: 0f,
  marketCap = market_data.market_cap[vs_currency] ?: 0L,
  marketCapRank = market_cap_rank,
  imageUrl = image.large.orEmpty(),
  sparklineIn7d = emptyList(),
  low24h = market_data.low_24h[vs_currency] ?: 0f,
  high24h = market_data.high_24h[vs_currency] ?: 0f,
  allTimeLow = market_data.atl[vs_currency] ?: 0f,
  allTimeHigh = market_data.ath[vs_currency] ?: 0f,
  circulatingSupply = market_data.circulating_supply ?: 0.0,
  totalSupply = market_data.total_supply ?: 0.0,
  maxSupply = market_data.max_supply ?: 0.0,
  links = links.toExternalModel(),
  description = description.en.orEmpty(),
)

fun LinksDto.toExternalModel() = Links(
  homepage = homepage?.firstOrNull().orEmpty(),
  facebook = "https://facebook.com/" + facebook_username.orEmpty(),
  twitter = "https://twitter.com/" + twitter_screen_name.orEmpty(),
  reddit = subreddit_url.orEmpty(),
)