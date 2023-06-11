package com.haroof.cryptohq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.haroof.coin_detail.navigation.coinDetailScreen
import com.haroof.coin_detail.navigation.navigateToCoinDetail
import com.haroof.converter.navigation.converterScreen
import com.haroof.converter.navigation.navigateToSelectCurrency
import com.haroof.converter.navigation.selectCurrencyScreen
import com.haroof.home.navigation.homeRoute
import com.haroof.home.navigation.homeScreen
import com.haroof.market.navigation.marketScreen
import com.haroof.market.navigation.navigateToMarket
import com.haroof.settings.navigation.aboutScreen
import com.haroof.settings.navigation.navigateToAbout
import com.haroof.settings.navigation.navigateToTermsOfService
import com.haroof.settings.navigation.settingsScreen
import com.haroof.settings.navigation.termsOfServiceScreen
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
    homeScreen(
      onNavigateToCoinDetail = { coinId -> navController.navigateToCoinDetail(coinId) },
      onNavigateToMarket = { navController.navigateToMarket() }
    )
    marketScreen(
      onNavigateToCoinDetail = { coinId -> navController.navigateToCoinDetail(coinId) }
    )
    converterScreen(
      onNavigateToSelectCurrency = { isSourceCurrency ->
        navController.navigateToSelectCurrency(
          isDefaultCurrency = false,
          isSourceCurrency = isSourceCurrency
        )
      }
    )
    selectCurrencyScreen(
      onBackPressed = { navController.navigateUp() },
    )
    watchListScreen(
      onNavigateToCoinDetail = { coinId -> navController.navigateToCoinDetail(coinId) }
    )
    settingsScreen(
      onNavigateToCurrencySettings = {
        navController.navigateToSelectCurrency(
          isDefaultCurrency = true,
          isSourceCurrency = false
        )
      },
      onNavigateToTermsOfService = { navController.navigateToTermsOfService(isTermsOfService = it) },
      onNavigateToAbout = { navController.navigateToAbout() }
    )
    coinDetailScreen(
      onBackPressed = { navController.navigateUp() },
    )
    aboutScreen(
      onBackPressed = { navController.navigateUp() },
    )
    termsOfServiceScreen()
  }
}