package com.haroof.cryptohq

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.haroof.cryptohq.ui.CryptoHqApp
import com.haroof.designsystem.theme.CryptoHqTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    // handling the splash screen transition
    setTheme(R.style.Theme_CryptoHq)

    super.onCreate(savedInstanceState)
    setContent {
      CryptoHqTheme {
        CryptoHqApp()
      }
    }
  }
}