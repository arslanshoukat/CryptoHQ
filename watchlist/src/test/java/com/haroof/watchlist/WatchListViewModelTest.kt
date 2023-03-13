package com.haroof.watchlist

import com.haroof.testing.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class WatchListViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun stateIsInitiallyLoading() = runTest {
    val viewModel = WatchListViewModel()
    assertEquals(WatchListUiState.Loading, viewModel.state.value)
  }
}