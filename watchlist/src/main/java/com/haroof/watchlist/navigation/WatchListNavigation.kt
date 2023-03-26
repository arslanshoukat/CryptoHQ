package com.haroof.watchlist.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haroof.watchlist.WatchListRoute

const val watchListRoute = "watch_list"

fun NavController.navigateToWatchList(navOptions: NavOptions? = null) {
  navigate(
    route = watchListRoute,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.watchListScreen(onNavigateToCoinDetail: (coinId: String) -> Unit) {
  composable(route = watchListRoute) {
    WatchListRoute(onNavigateToCoinDetail = onNavigateToCoinDetail)
  }
}