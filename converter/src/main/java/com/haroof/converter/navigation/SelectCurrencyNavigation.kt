package com.haroof.converter.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haroof.converter.select_currency.SelectCurrencyRoute

const val selectCurrencyRoute = "select_currency"
const val isDefaultCurrencyArg = "isDefaultCurrency"
const val isSourceCurrencyArg = "isSourceCurrency"

internal class SelectCurrencyArgs(val isDefaultCurrency: Boolean, val isSourceCurrency: Boolean) {
  constructor(savedStateHandle: SavedStateHandle) :
    this(
      isDefaultCurrency = checkNotNull(savedStateHandle[isDefaultCurrencyArg]) as Boolean,
      isSourceCurrency = checkNotNull(savedStateHandle[isSourceCurrencyArg]) as Boolean
    )
}

fun NavController.navigateToSelectCurrency(
  isDefaultCurrency: Boolean,
  isSourceCurrency: Boolean,
  navOptions: NavOptions? = null
) {
  navigate(
    route = "$selectCurrencyRoute/$isDefaultCurrency/$isSourceCurrency",
    navOptions = navOptions
  )
}

fun NavGraphBuilder.selectCurrencyScreen(onBackPressed: () -> Unit) {
  composable(
    route = "$selectCurrencyRoute/{$isDefaultCurrencyArg}/{$isSourceCurrencyArg}",
    arguments = listOf(
      navArgument(isDefaultCurrencyArg) { type = NavType.BoolType },
      navArgument(isSourceCurrencyArg) { type = NavType.BoolType }
    )
  ) {
    SelectCurrencyRoute(onBackPressed = onBackPressed)
  }
}