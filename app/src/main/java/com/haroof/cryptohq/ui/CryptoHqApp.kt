package com.haroof.cryptohq.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.haroof.cryptohq.navigation.CryptoHqNavHost

@Composable
fun CryptoHqApp() {
  val navController: NavHostController = rememberNavController()

  Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
    CryptoHqNavHost(navController = navController)
  }
}
