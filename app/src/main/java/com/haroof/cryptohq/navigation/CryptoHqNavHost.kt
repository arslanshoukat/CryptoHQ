package com.haroof.cryptohq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.haroof.coin_detail.navigation.coinDetailScreen
import com.haroof.coin_detail.navigation.navigateToCoinDetail
import com.haroof.home.navigation.homeRoute
import com.haroof.home.navigation.homeScreen
import com.haroof.market.navigation.marketScreen
import com.haroof.watchlist.navigation.watchListScreen

@Composable
fun CryptoHqNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
  startDestination: String = homeRoute,
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier
  ) {
    homeScreen()
    marketScreen(
      onNavigateToCoinDetail = { coinId -> navController.navigateToCoinDetail(coinId) }
    )
    watchListScreen()
    coinDetailScreen()
  }
}