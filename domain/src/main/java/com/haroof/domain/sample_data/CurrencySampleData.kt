package com.haroof.domain.sample_data

import com.haroof.domain.model.CurrencyUiModel

object CurrencySampleData {

  val BTC = CurrencyUiModel(
    code = "btc",
    name = "Bitcoin",
    unit = "BTC",
    currentValue = 1.0,
    ratePerBtc = 1.0,
    type = "crypto",
  )
  val USD = CurrencyUiModel(
    code = "usd",
    name = "US Dollar",
    unit = "$",
    currentValue = 29909.535,
    ratePerBtc = 29909.535,
    type = "fiat",
  )
  val AED = CurrencyUiModel(
    code = "aed",
    name = "United Arab Emirates Dirham",
    unit = "DH",
    currentValue = 109830.805,
    ratePerBtc = 109830.805,
    type = "fiat",
  )

  val LIST = listOf(BTC, USD, AED)
}