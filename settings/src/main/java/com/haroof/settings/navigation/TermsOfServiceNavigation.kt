package com.haroof.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.haroof.settings.tos.TermsOfServiceRoute

const val termsOfServiceRoute = "termsOfService"
const val isTermsOfServiceArg = "isTermsOfService"

fun NavController.navigateToTermsOfService(
  isTermsOfService: Boolean,
  navOptions: NavOptions? = null
) {
  navigate(
    route = "$termsOfServiceRoute/$isTermsOfService",
    navOptions = navOptions
  )
}

fun NavGraphBuilder.termsOfServiceScreen() {
  composable(route = "$termsOfServiceRoute/{$isTermsOfServiceArg}",
    arguments = listOf(
      navArgument(isTermsOfServiceArg) { type = NavType.BoolType }
    )) {
    TermsOfServiceRoute(
      isTermsOfService = it.arguments?.getBoolean(isTermsOfServiceArg) ?: true,
    )
  }
}