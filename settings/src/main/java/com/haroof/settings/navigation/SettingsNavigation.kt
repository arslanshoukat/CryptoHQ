package com.haroof.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haroof.settings.SettingsRoute

const val settingsRoute = "settings"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
  navigate(
    route = settingsRoute,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.settingsScreen(
  onNavigateToCurrencySettings: () -> Unit,
  onNavigateToAbout: () -> Unit,
) {
  composable(route = settingsRoute) {
    SettingsRoute(
      onNavigateToCurrencySettings = onNavigateToCurrencySettings,
      onNavigateToAbout = onNavigateToAbout,
    )
  }
}