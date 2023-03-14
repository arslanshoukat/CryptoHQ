package com.haroof.cryptohq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.haroof.cryptohq.navigation.TopLevelDestination.HOME
import com.haroof.cryptohq.navigation.TopLevelDestination.MARKET
import com.haroof.cryptohq.navigation.TopLevelDestination.WATCH_LIST
import com.haroof.home.HomeRoute
import com.haroof.market.MarketRoute
import com.haroof.watchlist.WatchListRoute

@Composable
fun CryptoHqNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
  startDestination: String = HOME.route,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier
  ) {
    composable(HOME.route) {
      HomeRoute()
    }
    composable(MARKET.route) {
      MarketRoute()
    }
    composable(WATCH_LIST.route) {
      WatchListRoute()
    }
  }
}