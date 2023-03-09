package com.haroof.designsystem.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
  primary = black900,
  onPrimary = Color.White,
  primaryVariant = black900,
  secondary = lemonGreen500,
  onSecondary = black900,
  secondaryVariant = lemonGreen500,
  background = black50,
  onBackground = black900,
  surface = Color.White,
  onSurface = black900,
  error = Color.Red,
  onError = Color.White,
)

@Composable
fun CryptoHqTheme(content: @Composable () -> Unit) {
  // TODO: add support for dark theme

  MaterialTheme(
    colors = LightColorPalette,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}