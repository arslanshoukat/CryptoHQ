package com.haroof.domain.model

import androidx.annotation.DrawableRes
import com.haroof.common.util.getCountryFlagFromCurrencyCode
import com.haroof.data.model.Currency

data class CurrencyUiModel(
  val code: String,
  val name: String,
  val unit: String,
  val currentValue: Double,
  val ratePerBtc: Double,
  val type: String,
  @DrawableRes val countryFlag: Int,
)

fun Currency.toExternalModel() = CurrencyUiModel(
  code = code,
  name = name,
  unit = unit,
  currentValue = ratePerBtc,  //  initially set same as rate
  ratePerBtc = ratePerBtc,
  type = type,
  countryFlag = getCountryFlagFromCurrencyCode(code),
)

fun CurrencyUiModel.toDataModel() = Currency(
  code = code,
  name = name,
  unit = unit,
  ratePerBtc = ratePerBtc,
  type = type,
)