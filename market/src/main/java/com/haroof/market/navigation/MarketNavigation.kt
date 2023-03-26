package com.haroof.market.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haroof.market.MarketRoute

const val marketRoute = "market"

fun NavController.navigateToMarket(navOptions: NavOptions? = null) {
  navigate(
    route = marketRoute,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.marketScreen(onNavigateToCoinDetail: (coinId: String) -> Unit) {
  composable(route = marketRoute) {
    MarketRoute(
      onNavigateToCoinDetail = onNavigateToCoinDetail
    )
  }
}
