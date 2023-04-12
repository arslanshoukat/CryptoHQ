package com.haroof.market

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import coil.ImageLoader
import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.repository.CoinsRepository
import com.haroof.domain.GetCoinsUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.market.R.string
import com.haroof.testing.data.SimpleCoinTestData
import com.haroof.testing.data.WatchableDetailedCoinTestData
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

  private val coinsRepository = FakeCoinsRepository()
  private val getCoinsUseCase = GetCoinsUseCase(coinsRepository)

  @Before
  fun init() {
    hiltRule.inject()

    composeTestRule.setContent {
      MarketRoute(
        viewModel = MarketViewModel(getCoinsUseCase),
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

  //  Test double for coins repository in ui tests
  private class FakeCoinsRepository : CoinsRepository {
    override fun getCoins(vs_currency: String): Flow<List<Coin>> {
      return flowOf(SimpleCoinTestData.LIST.map { it.toDataModel() })
    }

    override fun getCoinsByIds(
      ids: List<String>,
      vs_currency: String,
      sparkline: Boolean
    ): Flow<List<Coin>> {
      return flowOf(SimpleCoinTestData.LIST.filter { it.id in ids }.map { it.toDataModel() })
    }

    override fun getDetailedCoinById(id: String, vs_currency: String): Flow<DetailedCoin> {
      return flowOf(WatchableDetailedCoinTestData.LIST.first { it.id == id }.toDataModel())
    }
  }
}