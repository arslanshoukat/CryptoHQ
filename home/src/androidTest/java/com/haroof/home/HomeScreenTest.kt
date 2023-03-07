package com.haroof.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.haroof.home.R.string
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun whenDataIsLoading_loadingIndicatorIsShown() {
    composeTestRule.setContent {
      HomeScreen(HomeUiState.Loading)
    }

    composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(string.loading_indicator))
      .assertIsDisplayed()
  }
}