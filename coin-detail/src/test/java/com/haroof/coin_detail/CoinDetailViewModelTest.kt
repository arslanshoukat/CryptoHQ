package com.haroof.coin_detail

import androidx.lifecycle.SavedStateHandle
import com.haroof.common.model.TimeFilter
import com.haroof.data.FakeData
import com.haroof.data.repository.fake.FakeChartRepository
import com.haroof.data.repository.fake.FakeCoinsRepository
import com.haroof.data.repository.fake.FakeWatchListRepository
import com.haroof.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinDetailViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val savedStateHandle = SavedStateHandle().apply {
    set("coinId", FakeData.DETAILED_COINS.first().id)
  }

  @Test
  fun stateIsInitiallyLoading() {
    val viewModel = CoinDetailViewModel(
      savedStateHandle = savedStateHandle,
      coinsRepository = FakeCoinsRepository(
        shouldThrowError = true,
        shouldReturnEmpty = false
      ),
      chartRepository = FakeChartRepository(),
      watchListRepository = FakeWatchListRepository(),
    )
    assertEquals(CoinDetailUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataFetchFailed_stateIsError() = runTest {
    val viewModel = CoinDetailViewModel(
      savedStateHandle = savedStateHandle,
      coinsRepository = FakeCoinsRepository(
        shouldThrowError = true,
        shouldReturnEmpty = false
      ),
      chartRepository = FakeChartRepository(),
      watchListRepository = FakeWatchListRepository(),
    )
    assertTrue(viewModel.uiState.value is CoinDetailUiState.Error)
  }

  @Test
  fun whenDataFetchIsSuccessful_stateIsSuccess() = runTest {
    val viewModel = CoinDetailViewModel(
      savedStateHandle = savedStateHandle,
      coinsRepository = FakeCoinsRepository(),
      chartRepository = FakeChartRepository(),
      watchListRepository = FakeWatchListRepository(),
    )
    assertEquals(
      CoinDetailUiState.Success(
        coin = FakeData.DETAILED_COINS.first { it.id == "bitcoin" },
        selectedTimeFilter = TimeFilter.ONE_WEEK,
        chartData = emptyList(),
        isFavorite = false,
      ),
      viewModel.uiState.value
    )
  }

  @Test
  fun whenCoinIsFavorited_AddedToWatchList() {
    val viewModel = CoinDetailViewModel(
      savedStateHandle = savedStateHandle,
      coinsRepository = FakeCoinsRepository(),
      chartRepository = FakeChartRepository(),
      watchListRepository = FakeWatchListRepository(),
    )
    viewModel.updateWatchListSelection(selected = true)
    assertEquals(true, viewModel.uiState.value.asSuccess()?.isFavorite)
  }

  @Test
  fun whenCoinIsUnfavorited_RemovedFromWatchList() {
    val viewModel = CoinDetailViewModel(
      savedStateHandle = savedStateHandle,
      coinsRepository = FakeCoinsRepository(),
      chartRepository = FakeChartRepository(),
      watchListRepository = FakeWatchListRepository(),
    )
    viewModel.updateWatchListSelection(selected = true)
    assertEquals(true, viewModel.uiState.value.asSuccess()?.isFavorite)

    viewModel.updateWatchListSelection(selected = false)
    assertEquals(false, viewModel.uiState.value.asSuccess()?.isFavorite)
  }
}