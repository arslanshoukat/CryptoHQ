package com.haroof.watchlist

import com.haroof.data.FakeData
import com.haroof.data.repository.fake.FakeCoinsRepository
import com.haroof.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WatchListViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var fakeCoinsRepository: FakeCoinsRepository
  private lateinit var viewModel: WatchListViewModel

  @Before
  fun setup() {
    fakeCoinsRepository = FakeCoinsRepository()
    viewModel = WatchListViewModel(fakeCoinsRepository)
  }

  @Test
  fun stateIsInitiallyLoading() = runTest {
    // Create an empty collector for the StateFlow
    val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

    assertEquals(WatchListUiState.Loading, viewModel.uiState.value)

    collectJob.cancel()
  }

  @Test
  fun whenDataLoadedSuccessfully_stateIsSuccess() = runTest {
    // Create an empty collector for the StateFlow
    val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

    assertEquals(WatchListUiState.Loading, viewModel.uiState.value)

    fakeCoinsRepository.emit(FakeData.COINS)
    assertEquals(WatchListUiState.Success(FakeData.COINS), viewModel.uiState.value)

    collectJob.cancel()
  }
}