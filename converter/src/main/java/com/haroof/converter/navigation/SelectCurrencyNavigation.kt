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
const val isSourceCurrencyArg = "isSourceCurrency"

internal class SelectCurrencyArgs(val isSourceCurrency: Boolean) {
  constructor(savedStateHandle: SavedStateHandle) :
    this(checkNotNull(savedStateHandle[isSourceCurrencyArg]) as Boolean)
}

fun NavController.navigateToSelectCurrency(
  isSourceCurrency: Boolean,
  navOptions: NavOptions? = null
) {
  navigate(
    route = "$selectCurrencyRoute/$isSourceCurrency",
    navOptions = navOptions
  )
}

fun NavGraphBuilder.selectCurrencyScreen(onBackPressed: () -> Unit) {
  composable(
    route = "$selectCurrencyRoute/{$isSourceCurrencyArg}",
    arguments = listOf(
      navArgument(isSourceCurrencyArg) { type = NavType.BoolType }
    )
  ) {
    SelectCurrencyRoute(onBackPressed = onBackPressed)
  }
}