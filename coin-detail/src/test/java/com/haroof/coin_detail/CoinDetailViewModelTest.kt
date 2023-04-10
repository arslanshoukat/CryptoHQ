package com.haroof.coin_detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.testIn
import com.haroof.common.model.TimeFilter
import com.haroof.data.FakeData
import com.haroof.data.model.ChartData
import com.haroof.domain.AddCoinToWatchListUseCase
import com.haroof.domain.GetChartDataUseCase
import com.haroof.domain.GetWatchableDetailedCoinUseCase
import com.haroof.domain.RemoveCoinFromWatchListUseCase
import com.haroof.domain.model.toDataModel
import com.haroof.testing.MainDispatcherRule
import com.haroof.testing.data.ChartEntryTestData
import com.haroof.testing.data.WatchableDetailedCoinTestData
import com.haroof.testing.repository.TestChartRepository
import com.haroof.testing.repository.TestCoinsRepository
import com.haroof.testing.repository.TestWatchListRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val coinId = FakeData.DETAILED_COINS.first().id
  private val savedStateHandle = SavedStateHandle(mapOf("coinId" to coinId))

  private val coinsRepository = TestCoinsRepository()
  private val watchListRepository = TestWatchListRepository()
  private val chartRepository = TestChartRepository()
  private lateinit var viewModel: CoinDetailViewModel

  @Before
  fun setup() {
    viewModel = CoinDetailViewModel(
      savedStateHandle = savedStateHandle,
      getWatchableDetailedCoin = GetWatchableDetailedCoinUseCase(
        coinsRepository,
        watchListRepository
      ),
      getChartData = GetChartDataUseCase(chartRepository),
      addCoinToWatchList = AddCoinToWatchListUseCase(watchListRepository),
      removeCoinFromWatchList = RemoveCoinFromWatchListUseCase(watchListRepository),
    )
  }

  @Test
  fun coinId_matchesCoinIdFromSavedStateHandle() {
    assertEquals(coinId, viewModel.coinId)
  }

  @Test
  fun bothStatesAreInitiallyLoading() = runTest {
    val coinDetailTurbine = viewModel.coinDetailUiState.testIn(backgroundScope)
    val chartTurbine = viewModel.chartUiState.testIn(backgroundScope)

    assertEquals(CoinDetailUiState.Loading, coinDetailTurbine.awaitItem())
    assertEquals(ChartUiState(loading = true), chartTurbine.awaitItem())
  }

  @Test
  fun whenDataFetchFailed_stateIsError() {
    assertTrue(viewModel.coinDetailUiState.value is CoinDetailUiState.Error)
  }

  @Test
  fun whenCoinDetailFetchIsSuccessful_coinDetailStateIsSuccess() = runTest {
    viewModel.coinDetailUiState.test {
      assertEquals(CoinDetailUiState.Loading, awaitItem())

      val expectedDetailCoin = WatchableDetailedCoinTestData.WATCHED_COIN

      coinsRepository.sendDetailedCoin(expectedDetailCoin.toDataModel())
      watchListRepository.sendWatchedCoinsIds(listOf(expectedDetailCoin.id))

      assertEquals(
        CoinDetailUiState.Success(coin = expectedDetailCoin),
        awaitItem()
      )
    }
  }

  @Test
  fun afterCoinDetailFetched_chartShouldBeFetched_chartStateIsSuccess() = runTest {
    val coinDetailTurbine = viewModel.coinDetailUiState.testIn(backgroundScope)
    val chartTurbine = viewModel.chartUiState.testIn(backgroundScope)

    //  initially both states are loading
    assertEquals(CoinDetailUiState.Loading, coinDetailTurbine.awaitItem())
    assertEquals(ChartUiState(loading = true), chartTurbine.awaitItem())

    val expectedDetailCoin = WatchableDetailedCoinTestData.WATCHED_COIN

    //  first we fetch coin details
    coinsRepository.sendDetailedCoin(expectedDetailCoin.toDataModel())
    watchListRepository.sendWatchedCoinsIds(listOf(expectedDetailCoin.id))

    //  check if it is successful
    assertEquals(
      CoinDetailUiState.Success(coin = expectedDetailCoin),
      coinDetailTurbine.awaitItem()
    )

    chartRepository.sendChartData(ChartData(ChartEntryTestData.LIST.map { it.toDataModel() }))

    assertEquals(ChartUiState(chartData = ChartEntryTestData.LIST), chartTurbine.awaitItem())
  }

  @Test
  fun whenCoinIsAddedToWatchList_itIsWatched() = runTest {
    viewModel.coinDetailUiState.test {
      assertEquals(CoinDetailUiState.Loading, awaitItem())

      val expectedDetailCoin = WatchableDetailedCoinTestData.NOT_WATCHED_COIN

      coinsRepository.sendDetailedCoin(expectedDetailCoin.toDataModel())
      watchListRepository.sendWatchedCoinsIds(emptyList())  //  nothing in watchlist

      var coinDetailUiState = awaitItem()
      assertEquals(
        CoinDetailUiState.Success(coin = expectedDetailCoin),
        coinDetailUiState
      )

      //  ensuring coin is not in watchlist
      assertEquals(false, coinDetailUiState.asSuccess()?.coin?.isWatched)

      viewModel.updateWatchListSelection(selected = true)

      coinDetailUiState = awaitItem()
      assertEquals(true, coinDetailUiState.asSuccess()?.coin?.isWatched)
    }
  }

  @Test
  fun whenCoinIsRemovedFromWatchList_itIsNotWatched() = runTest {
    viewModel.coinDetailUiState.test {
      assertEquals(CoinDetailUiState.Loading, awaitItem())

      val expectedDetailCoin = WatchableDetailedCoinTestData.WATCHED_COIN

      coinsRepository.sendDetailedCoin(expectedDetailCoin.toDataModel())
      watchListRepository.sendWatchedCoinsIds(listOf(expectedDetailCoin.id))

      var coinDetailUiState = awaitItem()
      assertEquals(
        CoinDetailUiState.Success(coin = expectedDetailCoin),
        coinDetailUiState
      )

      //  ensuring coin is already in watchlist
      assertEquals(true, coinDetailUiState.asSuccess()?.coin?.isWatched)

      viewModel.updateWatchListSelection(selected = false)

      coinDetailUiState = awaitItem()
      assertEquals(false, coinDetailUiState.asSuccess()?.coin?.isWatched)
    }
  }

  @Test
  fun whenChartTimeFilterIsUpdated_chartUiStateIsAlsoUpdated() {
    val chartUiState = ChartUiState(loading = true, selectedTimeFilter = TimeFilter.ONE_WEEK)
    assertEquals(chartUiState, viewModel.chartUiState.value)

    viewModel.updateTimeFilter(TimeFilter.ONE_YEAR)

    assertEquals(
      chartUiState.copy(selectedTimeFilter = TimeFilter.ONE_YEAR),
      viewModel.chartUiState.value
    )
  }
}