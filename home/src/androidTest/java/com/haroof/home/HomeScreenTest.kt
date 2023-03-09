package com.haroof.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.haroof.data.FakeData
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

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenDataIsLoaded_listIsShown() {
    composeTestRule.setContent {
      HomeScreen(HomeUiState.Success(FakeData.COINS))
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coins_list))
      .assertExists()
    composeTestRule
      .onNodeWithText(FakeData.COINS.first().name)
      .assertExists()
  }

  @Test
  fun whenDataFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      HomeScreen(HomeUiState.Error(IllegalStateException()))
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.error_message))
      .assertExists()
  }
}