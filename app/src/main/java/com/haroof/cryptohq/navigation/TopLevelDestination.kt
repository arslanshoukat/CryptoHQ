package com.haroof.cryptohq.navigation

import androidx.annotation.DrawableRes
import com.haroof.cryptohq.R.drawable

enum class TopLevelDestination(val route: String, @DrawableRes val icon: Int) {
  HOME("home", drawable.twotone_home_24)
}