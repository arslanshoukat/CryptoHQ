package com.haroof.testing.data

import com.haroof.domain.model.Currency

object CurrencyTestData {

  val BTC = Currency(
    alphabeticCode = "btc",
    name = "Bitcoin",
    unit = "BTC",
    currentValue = 1.0,
    ratePerBtc = 1.0,
    type = "crypto",
  )
  val USD = Currency(
    alphabeticCode = "usd",
    name = "US Dollar",
    unit = "$",
    currentValue = 29909.535,
    ratePerBtc = 29909.535,
    type = "fiat",
  )
  val AED = Currency(
    alphabeticCode = "aed",
    name = "United Arab Emirates Dirham",
    unit = "DH",
    currentValue = 109830.805,
    ratePerBtc = 109830.805,
    type = "fiat",
  )

  val LIST = listOf(BTC, USD, AED)
}