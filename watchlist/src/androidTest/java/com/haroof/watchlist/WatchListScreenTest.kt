package com.haroof.watchlist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import coil.ImageLoader
import com.haroof.data.FakeData
import com.haroof.watchlist.R.string
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.haroof.common.R as commonR

@HiltAndroidTest
class WatchListScreenTest {

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
  fun whenScreenIsOpened_loadingIndicatorIsShown() {
    composeTestRule.setContent {
      WatchListScreen(
        uiState = WatchListUiState.Loading,
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenDataIsLoaded_listIsShown() {
    composeTestRule.setContent {
      WatchListScreen(
        uiState = WatchListUiState.Success(FakeData.COINS),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.watch_list_coins))
      .assertExists()
    composeTestRule
      .onNodeWithText(FakeData.COINS.first().name)
      .assertExists()
  }

  @Test
  fun whenDataIsEmpty_emptyStateIsShown() {
    composeTestRule.setContent {
      WatchListScreen(
        uiState = WatchListUiState.Success(emptyList()),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.watch_list_coins))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.empty_state_content_desc))
      .assertExists()
  }
}