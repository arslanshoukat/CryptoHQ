package com.haroof.market

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import coil.ImageLoader
import com.haroof.market.R.string
import com.haroof.testing.data.SimpleCoinTestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import com.haroof.common.R as commonR

@HiltAndroidTest
class MarketScreenTest {

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
      MarketScreen(
        uiState = MarketUiState.Loading,
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
      MarketScreen(
        uiState = MarketUiState.Success(SimpleCoinTestData.LIST.sortedBy { it.marketCapRank }),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.market_coins_list_content_desc))
      .assertExists()
  }

  @Test
  fun whenDataIsLoadedButEmpty_emptyStateIsShown() {
    composeTestRule.setContent {
      MarketScreen(
        uiState = MarketUiState.Empty,
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(commonR.string.loading_indicator))
      .assertDoesNotExist()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.market_empty_state_content_description))
      .assertExists()
  }

  @Test
  fun whenDataFailedToLoad_errorIsShown() {
    composeTestRule.setContent {
      MarketScreen(
        uiState = MarketUiState.Error(IllegalStateException()),
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
  fun whenDataIsSortedByPriceAsc_PriceAscendingSortIconIsShown() {
    composeTestRule.setContent {
      MarketScreen(
        uiState = MarketUiState.Success(
          coins = SimpleCoinTestData.LIST.sortedBy { it.currentPrice },
          sortBy = SortBy.PRICE,
          sortOrder = SortOrder.ASCENDING,
        ),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(
        composeTestRule.activity.getString(string.price_title) + " "
          + composeTestRule.activity.getString(string.sort_ascending_icon_content_desc)
      )
      .assertExists()
    composeTestRule
      .onNodeWithContentDescription(
        composeTestRule.activity.getString(string.rank_title) + " "
          + composeTestRule.activity.getString(string.sort_ascending_icon_content_desc)
      )
      .assertDoesNotExist()
  }

  @Test
  fun whenDataIsSortedByPriceDesc_PriceDescendingSortIconIsShown() {
    composeTestRule.setContent {
      MarketScreen(
        uiState = MarketUiState.Success(
          coins = SimpleCoinTestData.LIST.sortedByDescending { it.currentPrice },
          sortBy = SortBy.PRICE,
          sortOrder = SortOrder.DESCENDING,
        ),
        imageLoader = imageLoader
      )
    }

    composeTestRule
      .onNodeWithContentDescription(
        composeTestRule.activity.getString(string.price_title) + " "
          + composeTestRule.activity.getString(string.sort_descending_icon_content_desc)
      )
      .assertExists()
    composeTestRule
      .onNodeWithContentDescription(
        composeTestRule.activity.getString(string.rank_title) + " "
          + composeTestRule.activity.getString(string.sort_descending_icon_content_desc)
      )
      .assertDoesNotExist()
  }
}