package com.haroof.coin_detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import coil.ImageLoader
import com.haroof.testing.data.ChartEntryTestData
import com.haroof.testing.data.WatchableDetailedCoinTestData
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
  fun whenCoinDetailIsLoading_loadingIndicatorIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Loading,
        chartUiState = ChartUiState(loading = true),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertExists()
  }

  @Test
  fun whenCoinDetailFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Error(IllegalStateException()),
        chartUiState = ChartUiState(loading = true),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.error_message_content_desc))
      .assertExists()
  }

  @Test
  fun whenCoinDetailLoadedSuccessfully_contentIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.LIST.first(),
        ),
        chartUiState = ChartUiState(loading = true),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coin_detail_content_desc))
      .assertExists()
  }

  @Test
  fun whenCoinDetailLoadedAndDisplayed_chartIsInitiallyLoading() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.LIST.first(),
        ),
        chartUiState = ChartUiState(loading = true),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coin_detail_content_desc))
      .assertExists()

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.chart_loading_indicator_content_desc))
      .assertExists()
  }

  @Test
  fun afterCoinDetailLoaded_whenChartDataFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.LIST.first(),
        ),
        chartUiState = ChartUiState(exception = IllegalStateException()),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coin_detail_content_desc))
      .assertExists()

    composeTestRule
      .onNodeWithText(composeTestRule.activity.getString(R.string.chart_data_fetch_error))
      .assertExists()
  }

  @Test
  fun afterCoinDetailLoaded_whenChartDataLoadedButEmpty_emptyMessageIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.LIST.first(),
        ),
        chartUiState = ChartUiState(chartData = emptyList()),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coin_detail_content_desc))
      .assertExists()

    composeTestRule
      .onNodeWithText(composeTestRule.activity.getString(R.string.empty_chart_data_message))
      .assertExists()
  }

  @Test
  fun afterCoinDetailLoaded_whenChartDataFetchedSuccessfully_chartIsShown() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.LIST.first(),
        ),
        chartUiState = ChartUiState(chartData = ChartEntryTestData.LIST),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.coin_detail_content_desc))
      .assertExists()

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.chart_loading_indicator_content_desc))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.chart_content_desc))
      .assertExists()
  }

  @Test
  fun whenCoinIsInWatchList_favoriteIconIsChecked() {
    composeTestRule.setContent {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.WATCHED_COIN,
        ),
        chartUiState = ChartUiState(loading = true),
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
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinTestData.NOT_WATCHED_COIN,
        ),
        chartUiState = ChartUiState(loading = true),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(R.string.favorite_icon_content_desc))
      .assertIsOff()
  }
}