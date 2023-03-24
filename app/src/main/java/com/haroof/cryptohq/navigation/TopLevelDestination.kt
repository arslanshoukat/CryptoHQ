package com.haroof.cryptohq.navigation

import androidx.annotation.DrawableRes
import com.haroof.cryptohq.R.drawable

// TODO: clean up top level navigation
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
  MARKET(
    route = "Market",
    selectedIcon = drawable.sharp_market_24,
    unselectedIcon = drawable.outline_market_24
  ),
  WATCH_LIST(
    route = "Watch List",
    selectedIcon = drawable.sharp_star_24,
    unselectedIcon = drawable.outline_star_24
  ),
  COIN_DETAIL(
    route = "CoinDetail/",
    selectedIcon = drawable.sharp_star_24,
    unselectedIcon = drawable.outline_star_24
  ),
}