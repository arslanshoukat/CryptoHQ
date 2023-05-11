package com.haroof.cryptohq.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.haroof.converter.navigation.converterRoute
import com.haroof.converter.navigation.navigateToConverter
import com.haroof.cryptohq.navigation.TopLevelDestination
import com.haroof.cryptohq.navigation.TopLevelDestination.CONVERTER
import com.haroof.cryptohq.navigation.TopLevelDestination.HOME
import com.haroof.cryptohq.navigation.TopLevelDestination.MARKET
import com.haroof.cryptohq.navigation.TopLevelDestination.SETTINGS
import com.haroof.cryptohq.navigation.TopLevelDestination.WATCH_LIST
import com.haroof.home.navigation.homeRoute
import com.haroof.home.navigation.navigateToHome
import com.haroof.market.navigation.marketRoute
import com.haroof.market.navigation.navigateToMarket
import com.haroof.settings.navigation.navigateToSettings
import com.haroof.settings.navigation.settingsRoute
import com.haroof.watchlist.navigation.navigateToWatchList
import com.haroof.watchlist.navigation.watchListRoute

@Composable
fun rememberAppState(
  navController: NavHostController = rememberNavController(),
): CryptoHqAppState {
  return remember(navController) {
    CryptoHqAppState(navController)
  }
}

@Stable
class CryptoHqAppState(
  val navController: NavHostController
) {
  val currentDestination: NavDestination?
    @Composable get() = navController
      .currentBackStackEntryAsState().value?.destination

  val currentTopLevelDestination: TopLevelDestination?
    @Composable get() = when (currentDestination?.route) {
      homeRoute -> HOME
      marketRoute -> MARKET
      watchListRoute -> WATCH_LIST
      converterRoute -> CONVERTER
      settingsRoute -> SETTINGS
      else -> null
    }

  val shouldShowBottomNavBar: Boolean
    @Composable get() = currentTopLevelDestination != null

  val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

  /**
   * UI logic for navigating to a top level destination in the app. Top level destinations have
   * only one copy of the destination of the back stack, and save and restore state whenever you
   * navigate to and from it.
   *
   * @param topLevelDestination: The destination the app needs to navigate to.
   */
  fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
    val topLevelNavOptions = navOptions {
      // Pop up to the start destination of the graph to
      // avoid building up a large stack of destinations
      // on the back stack as users select items
      popUpTo(navController.graph.findStartDestination().id) {
        saveState = true
      }
      // Avoid multiple copies of the same destination when
      // reselecting the same item
      launchSingleTop = true
      // Restore state when reselecting a previously selected item
      restoreState = true
    }

    when (topLevelDestination) {
      HOME -> navController.navigateToHome(topLevelNavOptions)
      MARKET -> navController.navigateToMarket(topLevelNavOptions)
      WATCH_LIST -> navController.navigateToWatchList(topLevelNavOptions)
      CONVERTER -> navController.navigateToConverter(topLevelNavOptions)
      SETTINGS -> navController.navigateToSettings(topLevelNavOptions)
    }
  }
}