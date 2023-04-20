package com.haroof.network.model

import kotlinx.serialization.Serializable

@Serializable
data class GetExchangeRatesResponse(
  val rates: Map<String, ExchangeRateDto>
)
