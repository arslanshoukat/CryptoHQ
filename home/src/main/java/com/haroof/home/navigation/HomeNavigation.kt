package com.haroof.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haroof.home.HomeRoute

const val homeRoute = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
  navigate(
    route = homeRoute,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.homeScreen(
  onNavigateToCoinDetail: (coinId: String) -> Unit,
  onNavigateToMarket: () -> Unit,
) {
  composable(route = homeRoute) {
    HomeRoute(
      onNavigateToCoinDetail = onNavigateToCoinDetail,
      onNavigateToMarket = onNavigateToMarket
    )
  }
}