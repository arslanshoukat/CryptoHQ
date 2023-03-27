package com.haroof.cryptohq.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.haroof.cryptohq.navigation.CryptoHqNavHost
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.black900

@Composable
fun CryptoHqApp(
  appState: CryptoHqAppState = rememberAppState()
) {

  Scaffold(
    bottomBar = {
      if (appState.shouldShowBottomNavBar) BottomNavBar(appState)
    }
  ) { padding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
      color = MaterialTheme.colors.background
    ) {
      val systemUiController = rememberSystemUiController()
      //  todo: user dark icons when using dark theme

      DisposableEffect(systemUiController) {
        systemUiController.setStatusBarColor(color = black900, darkIcons = false)

        onDispose {}
      }

      CryptoHqNavHost(navController = appState.navController)
    }
  }
}

@Composable
private fun BottomNavBar(appState: CryptoHqAppState) {
  BottomNavigation(
    backgroundColor = MaterialTheme.colors.surface,
    contentColor = contentColorFor(MaterialTheme.colors.surface),
    modifier = Modifier.fillMaxWidth()
  ) {
    appState.topLevelDestinations.forEach { destination ->
      CryptoHqBottomNavItem(
        selected = appState.currentTopLevelDestination == destination,
        onClick = { appState.navigateToTopLevelDestination(destination) },
        icon = {
          Icon(
            painter = painterResource(id = destination.unselectedIcon),
            contentDescription = destination.title
          )
        },
        selectedIcon = {
          Icon(
            painter = painterResource(id = destination.selectedIcon),
            contentDescription = destination.title
          )
        },
        label = { Text(text = destination.title, style = MaterialTheme.typography.subtitle2) },
      )
    }
  }
}

@Composable
private fun RowScope.CryptoHqBottomNavItem(
  selected: Boolean,
  onClick: () -> Unit,
  icon: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
  label: @Composable (() -> Unit)? = null,
  alwaysShowLabel: Boolean = true,
  selectedIcon: @Composable () -> Unit = icon,
) {
  BottomNavigationItem(
    selected = selected,
    onClick = onClick,
    icon = if (selected) selectedIcon else icon,
    modifier = modifier,
    enabled = enabled,
    label = label,
    alwaysShowLabel = alwaysShowLabel,
  )
}

@Preview(showBackground = true)
@Composable
fun CryptoHqAppPreview() {
  CryptoHqTheme {
    CryptoHqApp()
  }
}
