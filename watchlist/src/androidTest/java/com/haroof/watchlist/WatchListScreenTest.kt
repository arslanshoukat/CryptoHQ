package com.haroof.watchlist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test
import com.haroof.common.R as commonR

class WatchListScreenTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun whenScreenIsOpened_loadingIndicatorIsShown() {
    composeTestRule.setContent {
      WatchListScreen(uiState = WatchListUiState.Loading)
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }
}