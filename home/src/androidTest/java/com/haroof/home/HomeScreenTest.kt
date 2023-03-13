package com.haroof.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import coil.ImageLoader
import com.haroof.data.FakeData
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
      HomeScreen(HomeUiState.Loading, imageLoader)
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenDataIsLoaded_listIsShown() {
    composeTestRule.setContent {
      HomeScreen(HomeUiState.Success(FakeData.COINS), imageLoader)
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
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
      HomeScreen(HomeUiState.Error(IllegalStateException()), imageLoader)
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.error_message))
      .assertExists()
  }
}