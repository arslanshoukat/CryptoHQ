package com.haroof.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DetailedCoinDto(
  val id: String,
  val name: String,
  val symbol: String,
  val market_cap_rank: Int,
  val market_data: MarketDataDto,
  val image: ImageDto,
  val description: DescriptionDto,
  val links: LinksDto,
)

@Serializable
data class MarketDataDto(
  val current_price: Map<String, Float>,
  val price_change_24h_in_currency: Map<String, Float>,
  val price_change_percentage_24h_in_currency: Map<String, Float>,
  val price_change_percentage_7d_in_currency: Map<String, Float>,
  val price_change_percentage_30d_in_currency: Map<String, Float>,
  val price_change_percentage_1y_in_currency: Map<String, Float>,

  val low_24h: Map<String, Float>,
  val high_24h: Map<String, Float>,

  val atl: Map<String, Float>,
  val atl_change_percentage: Map<String, Float>,
  val atl_date: Map<String, String>,

  val ath: Map<String, Float>,
  val ath_change_percentage: Map<String, Float>,
  val ath_date: Map<String, String>,

  val market_cap_rank: Int,
  val market_cap: Map<String, Long>,
  val circulating_supply: Double?,
  val total_supply: Double?,
  val max_supply: Double?,
  val total_volume: Map<String, Double>
)

@Serializable
data class ImageDto(
  val large: String?,
  val small: String?,
  val thumb: String?
)

@Serializable
data class DescriptionDto(
  val en: String?
)

@Serializable
data class LinksDto(
  val homepage: List<String?>?,
  val facebook_username: String?,
  val twitter_screen_name: String?,
  val subreddit_url: String?,
)




