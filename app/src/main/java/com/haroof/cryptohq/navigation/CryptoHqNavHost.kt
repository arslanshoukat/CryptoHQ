package com.haroof.cryptohq.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.haroof.cryptohq.navigation.TopLevelDestination.HOME
import com.haroof.home.HomeRoute

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
  }
}