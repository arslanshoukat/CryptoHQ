package com.haroof.converter.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haroof.converter.ConverterRoute

const val converterRoute = "converter"

fun NavController.navigateToConverter(navOptions: NavOptions? = null) {
  navigate(
    route = converterRoute,
    navOptions = navOptions
  )
}

fun NavGraphBuilder.converterScreen() {
  composable(route = converterRoute) {
    ConverterRoute()
  }
}