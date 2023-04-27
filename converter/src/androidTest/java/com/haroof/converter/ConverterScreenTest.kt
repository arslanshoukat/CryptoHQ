package com.haroof.converter

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.haroof.testing.data.CurrencyTestData
import org.junit.Rule
import org.junit.Test
import com.haroof.common.R as commonR

class ConverterScreenTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun whenFetchingData_showLoading() {
    composeTestRule.setContent {
      ConverterScreen(
        uiState = ConverterUiState.Loading
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenFailedToFetchData_showError() {
    composeTestRule.setContent {
      ConverterScreen(
        uiState = ConverterUiState.Error(IllegalStateException())
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.error_message_content_desc))
      .assertExists()
  }

  @Test
  fun whenDataLoaded_showContent() {
    composeTestRule.setContent {
      ConverterScreen(
        uiState = ConverterUiState.Success(
          from = CurrencyTestData.BTC,
          to = CurrencyTestData.USD,
        )
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.converter_content_desc))
      .assertExists()
  }

  @Test
  fun sourceCurrencyHasCalculateIcon() {
    composeTestRule.setContent {
      ConverterScreen(
        uiState = ConverterUiState.Success(
          from = CurrencyTestData.BTC,
          to = CurrencyTestData.USD,
        )
      )
    }

    composeTestRule
      .onNode(
        matcher = hasContentDescription(composeTestRule.activity.getString(R.string.source_currency_value_content_desc)).and(
          hasAnySibling(hasContentDescription(composeTestRule.activity.getString(R.string.calculate_icon_content_desc)))
        ),
      )
      .assertExists()
  }

  @Test
  fun toCurrencyHasEqualsIcon() {
    composeTestRule.setContent {
      ConverterScreen(
        uiState = ConverterUiState.Success(
          from = CurrencyTestData.BTC,
          to = CurrencyTestData.USD,
        )
      )
    }

    composeTestRule
      .onNode(
        matcher = hasContentDescription(composeTestRule.activity.getString(R.string.currency_value_content_desc)).and(
          hasAnySibling(hasText(composeTestRule.activity.getString(R.string.equals_symbol)))
        ),
        useUnmergedTree = true
      )
      .assertExists()
  }
}