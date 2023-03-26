package com.haroof.coin_detail.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haroof.coin_detail.CoinDetailRoute

const val coinDetailRoute = "coin_detail"
const val coinIdArg = "coinId"

internal class CoinDetailArgs(val coinId: String) {
  constructor(savedStateHandle: SavedStateHandle) :
    this(checkNotNull(savedStateHandle[coinIdArg]) as String)
}

// TODO: add safe nav args handling
fun NavController.navigateToCoinDetail(coinId: String, navOptions: NavOptions? = null) {
  navigate(
    route = "$coinDetailRoute/$coinId",
    navOptions = navOptions
  )
}

fun NavGraphBuilder.coinDetailScreen() {
  composable(
    route = "$coinDetailRoute/{$coinIdArg}",
    arguments = listOf(
      navArgument(coinIdArg) { type = NavType.StringType }
    )
  ) {
    CoinDetailRoute()
  }
}