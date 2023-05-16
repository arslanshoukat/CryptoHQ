package com.haroof.cryptohq.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.haroof.cryptohq.navigation.CryptoHqNavHost
import com.haroof.cryptohq.navigation.TopLevelDestination.CONVERTER
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.black500
import com.haroof.designsystem.theme.black900

@Composable
fun CryptoHqApp(
  appState: CryptoHqAppState = rememberAppState()
) {

  Scaffold(
    bottomBar = {
      if (appState.shouldShowBottomNavBar) BottomNavBar(appState)
    },
    floatingActionButton = {
      FloatingActionButton(
        elevation = FloatingActionButtonDefaults.elevation(2.dp, 4.dp, 0.dp, 0.dp),
        onClick = { appState.navigateToTopLevelDestination(CONVERTER) }) {
        Icon(
          painter = painterResource(CONVERTER.unselectedIcon),
          contentDescription = null,
          modifier = Modifier.size(32.dp)
        )
      }
    },
    floatingActionButtonPosition = FabPosition.Center,
    isFloatingActionButtonDocked = true,
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
  BottomAppBar(
    backgroundColor = MaterialTheme.colors.surface,
    contentColor = contentColorFor(MaterialTheme.colors.surface),
    cutoutShape = CircleShape,
    modifier = Modifier.fillMaxWidth()
  ) {
    appState.topLevelDestinations.forEach { destination ->
      CryptoHqBottomNavItem(
        selected = appState.currentTopLevelDestination == destination,
        onClick = { appState.navigateToTopLevelDestination(destination) },
        icon = {
          //  show icon for all top level destinations except converter
          if (destination != CONVERTER) {
            Icon(
              painter = painterResource(id = destination.unselectedIcon),
              contentDescription = null,
              tint = black500
            )
          }
        },
        selectedIcon = {
          //  show selected icon for all top level destinations except converter
          if (destination != CONVERTER) {
            Icon(
              painter = painterResource(id = destination.selectedIcon),
              contentDescription = null
            )
          }
        },
        enabled = destination != CONVERTER  //  disable clicks on converter item in bottom nav
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
  alwaysShowLabel: Boolean = false,
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
