package com.haroof.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import coil.ImageLoader
import com.haroof.home.R.string
import com.haroof.testing.data.SimpleCoinTestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.haroof.common.R as commonR

@HiltAndroidTest
class HomeScreenTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Inject
  lateinit var imageLoader: ImageLoader

  @Before
  fun init() {
    hiltRule.inject()
  }

  @Test
  fun whenDataIsLoading_loadingIndicatorIsShown() {
    composeTestRule.setContent {
      HomeScreen(
        uiState = HomeUiState.Loading,
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenDataIsLoaded_marketAndGainersAndLosersShown() {
    composeTestRule.setContent {
      HomeScreen(
        uiState = HomeUiState.Success(
          gainersAndLosers = SimpleCoinTestData.GAINERS_AND_LOSERS,
          marketCoins = SimpleCoinTestData.LIST,
        ),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coins_list))
      .assertExists()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.gainers_and_losers_list))
      .assertExists()
  }

  @Test
  fun whenDataFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      HomeScreen(
        uiState = HomeUiState.Error(IllegalStateException()),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.error_message_content_desc))
      .assertExists()
  }
}