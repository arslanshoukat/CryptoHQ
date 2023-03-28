package com.haroof.coin_detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import coil.ImageLoader
import com.haroof.common.model.TimeFilter
import com.haroof.data.FakeData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.haroof.common.R as commonR

@HiltAndroidTest
class CoinDetailScreenTest {

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
      CoinDetailScreen(
        uiState = CoinDetailUiState.Loading,
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenDataFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        uiState = CoinDetailUiState.Error(IllegalStateException()),
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

  @Test
  fun whenDataLoadedSuccessfully_contentIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        uiState = CoinDetailUiState.Success(
          coin = FakeData.DETAILED_COINS.first(),
          selectedTimeFilter = TimeFilter.ONE_WEEK,
          chartData = emptyList(),
          isFavorite = false,
        ),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coin_detail_content_desc))
      .assertExists()
  }

  @Test
  fun whenCoinIsInWatchList_favoriteIconIsChecked() {
    composeTestRule.setContent {
      CoinDetailScreen(
        uiState = CoinDetailUiState.Success(
          coin = FakeData.DETAILED_COINS.first(),
          selectedTimeFilter = TimeFilter.ONE_WEEK,
          chartData = emptyList(),
          isFavorite = true,
        ),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.favorite_icon_content_desc))
      .assertIsOn()
  }

  @Test
  fun whenCoinIsNotInWatchList_favoriteIconIsUnchecked() {
    composeTestRule.setContent {
      CoinDetailScreen(
        uiState = CoinDetailUiState.Success(
          coin = FakeData.DETAILED_COINS.first(),
          selectedTimeFilter = TimeFilter.ONE_WEEK,
          chartData = emptyList(),
          isFavorite = false,
        ),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.favorite_icon_content_desc))
      .assertIsOff()
  }
}