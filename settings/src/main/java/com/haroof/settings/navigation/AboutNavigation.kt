package com.haroof.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haroof.settings.about.AboutRoute

const val aboutRoute = "about"

fun NavController.navigateToAbout(navOptions: NavOptions? = null) {
  navigate(
    route = aboutRoute,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.aboutScreen(onBackPressed: () -> Unit) {
  composable(route = aboutRoute) {
    AboutRoute(onBackPressed = onBackPressed)
  }
}