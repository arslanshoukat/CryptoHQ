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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.haroof.cryptohq.navigation.CryptoHqNavHost
import com.haroof.designsystem.theme.CryptoHqTheme

@Composable
fun CryptoHqApp(
  appState: CryptoHqAppState = rememberAppState()
) {

  Scaffold(
    bottomBar = {
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
                contentDescription = destination.route
              )
            },
            selectedIcon = {
              Icon(
                painter = painterResource(id = destination.selectedIcon),
                contentDescription = destination.route
              )
            },
            label = { Text(text = destination.route, style = MaterialTheme.typography.subtitle2) },
          )
        }
      }
    }
  ) { padding ->
    Surface(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding),
      color = MaterialTheme.colors.background
    ) {
      CryptoHqNavHost(navController = appState.navController)
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

@Preview
@Composable
fun CryptoHqAppPreview() {
  CryptoHqTheme {
    CryptoHqApp()
  }
}
