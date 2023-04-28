package com.haroof.converter.util

fun convertFromSourceToTargetCurrency(
  sourceCurrencyValue: Double,
  sourceCurrencyRatePerBtc: Double,
  targetCurrencyRatePerBtc: Double,
): Double {
  //  first we convert source currency to bitcoins
  val sourceCurrencyInBitcoins = sourceCurrencyValue / sourceCurrencyRatePerBtc
  //  then we convert these bitcoins to target currency
  return targetCurrencyRatePerBtc * sourceCurrencyInBitcoins
}