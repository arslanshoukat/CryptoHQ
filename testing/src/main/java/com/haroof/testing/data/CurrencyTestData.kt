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
  val PKR = CurrencyUiModel(
    code = "pkr",
    name = "Pakistani Rupee",
    unit = "₨",
    currentValue = 8074599.326,
    ratePerBtc = 8074599.326,
    type = "fiat",
    countryFlag = com.haroof.common.R.drawable.pk
  )
  val XAU = CurrencyUiModel(
    code = "xau",
    name = "Gold - Troy Ounce",
    unit = "XAU",
    currentValue = 14.44,
    ratePerBtc = 14.44,
    type = "commodity",
    countryFlag = com.haroof.common.R.drawable.flag_placeholder
  )

  val LIST = listOf(BTC, USD, AED, PKR, XAU)
}