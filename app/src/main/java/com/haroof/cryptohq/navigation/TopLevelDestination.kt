package com.haroof.cryptohq.navigation

import androidx.annotation.DrawableRes
import com.haroof.cryptohq.R.drawable

enum class TopLevelDestination(
  val route: String,
  @DrawableRes val selectedIcon: Int,
  @DrawableRes val unselectedIcon: Int,
) {
  HOME(
    route = "Home",
    selectedIcon = drawable.sharp_dashboard_24,
    unselectedIcon = drawable.outline_dashboard_24
  ),
  WATCH_LIST(
    route = "Watch List",
    selectedIcon = drawable.sharp_star_24,
    unselectedIcon = drawable.outline_star_24
  ),
}