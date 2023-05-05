package com.haroof.testing.data

import com.haroof.domain.model.CurrencyUiModel

object CurrencyTestData {

  val BTC = CurrencyUiModel(
    code = "btc",
    name = "Bitcoin",
    unit = "BTC",
    currentValue = 1.0,
    ratePerBtc = 1.0,
    type = "crypto",
    countryFlag = com.haroof.common.R.drawable.btc,
  )
  val USD = CurrencyUiModel(
    code = "usd",
    name = "US Dollar",
    unit = "$",
    currentValue = 25000.0,
    ratePerBtc = 25000.0,
    type = "fiat",
    countryFlag = com.haroof.common.R.drawable.us,
  )
  val AED = CurrencyUiModel(
    code = "aed",
    name = "United Arab Emirates Dirham",
    unit = "DH",
    currentValue = 100000.0,
    ratePerBtc = 100000.0,
    type = "fiat",
    countryFlag = com.haroof.common.R.drawable.ae,
  )

  val LIST = listOf(BTC, USD, AED)
}