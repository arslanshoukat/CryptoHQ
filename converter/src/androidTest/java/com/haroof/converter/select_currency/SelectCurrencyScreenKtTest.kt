package com.haroof.converter.select_currency

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.haroof.converter.R
import com.haroof.testing.data.CurrencyTestData
import org.junit.Rule
import org.junit.Test
import com.haroof.common.R as commonR

class SelectCurrencyScreenTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun whenFetchingData_showLoading() {
    composeTestRule.setContent {
      SelectCurrencyScreen(
        uiState = SelectCurrencyUiState.Loading
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenDataLoaded_showContent() {
    composeTestRule.setContent {
      SelectCurrencyScreen(
        uiState = SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST,
          selectedCurrencyCode = CurrencyTestData.USD.code,
        )
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.selectable_currencies_content_desc))
      .assertExists()
  }

  @Test
  fun selectedCurrencyHasSelectedIcon() {
    val selectedCurrencyCode = CurrencyTestData.USD.code
    composeTestRule.setContent {
      SelectCurrencyScreen(
        uiState = SelectCurrencyUiState.Success(
          selectableCurrencies = CurrencyTestData.LIST,
          selectedCurrencyCode = selectedCurrencyCode,
        )
      )
    }

    composeTestRule
      .onNode(
        matcher = hasContentDescription(composeTestRule.activity.getString(R.string.selected_icon_content_desc))
          .and(
            hasAnySibling(
              hasText(
                text = selectedCurrencyCode,
                ignoreCase = true
              )
            )
          ),
        useUnmergedTree = true
      )
      .assertExists()
  }
}