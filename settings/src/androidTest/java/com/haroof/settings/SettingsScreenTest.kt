package com.haroof.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.haroof.settings.R.string
import com.haroof.testing.data.CurrencyTestData
import org.junit.Rule
import org.junit.Test
import com.haroof.common.R as commonR

class SettingsScreenTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun whenUserSettingsAreLoading_showLoadingIndicator() {
    composeTestRule.setContent {
      SettingsScreen(
        uiState = SettingsUiState.Loading
      )
    }

    composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenUserSettingsFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      SettingsScreen(
        uiState = SettingsUiState.Error(IllegalStateException()),
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.error_message_content_desc))
      .assertExists()
  }

  @Test
  fun whenUserSettingsLoaded_showContent() {
    composeTestRule.setContent {
      SettingsScreen(
        uiState = SettingsUiState.Success(
          currency = CurrencyTestData.USD.code,
        ),
      )
    }

    composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.currency))
      .assertExists()
    composeTestRule.onNodeWithText(CurrencyTestData.USD.code, ignoreCase = true)
      .assertExists()
    composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.terms_of_service))
      .assertExists()
    composeTestRule.onNodeWithText(composeTestRule.activity.getString(string.about))
      .assertExists()
  }
}