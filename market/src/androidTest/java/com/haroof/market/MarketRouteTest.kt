package com.haroof.market

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import coil.ImageLoader
import com.haroof.data.repository.fake.FakeCoinsRepository
import com.haroof.market.R.string
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MarketRouteTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Inject
  lateinit var imageLoader: ImageLoader

  @Before
  fun init() {
    hiltRule.inject()

    composeTestRule.setContent {
      MarketRoute(
        viewModel = MarketViewModel(FakeCoinsRepository()),
        onNavigateToCoinDetail = {},
        imageLoader = imageLoader
      )
    }
  }

  @Test
  fun whenSortingByPriceAsc_PriceAscendingSortIconIsShown() {
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.price_title))
      .performClick()

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
  fun whenSortingByPriceDesc_PriceDescendingSortIconIsShown() {
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.price_title))
      .performClick()
    composeTestRule
      .onNodeWithContentDescription(composeTestRule.activity.getString(string.price_title))
      .performClick()

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