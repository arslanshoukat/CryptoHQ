package com.haroof.cryptohq.navigation

import androidx.annotation.DrawableRes
import com.haroof.cryptohq.R.drawable

enum class TopLevelDestination(
  val title: String,
  @DrawableRes val selectedIcon: Int,
  @DrawableRes val unselectedIcon: Int,
) {
  HOME(
    title = "Home",
    selectedIcon = drawable.sharp_dashboard_24,
    unselectedIcon = drawable.outline_dashboard_24
  ),
  MARKET(
    title = "Market",
    selectedIcon = drawable.sharp_market_24,
    unselectedIcon = drawable.outline_market_24
  ),
  WATCH_LIST(
    title = "Watch List",
    selectedIcon = drawable.sharp_star_24,
    unselectedIcon = drawable.outline_star_24
  ),
  CONVERTER(
    title = "Converter",
    selectedIcon = drawable.sharp_swap_horizontal_circle_24,
    unselectedIcon = drawable.outline_swap_horizontal_circle_24
  ),
}