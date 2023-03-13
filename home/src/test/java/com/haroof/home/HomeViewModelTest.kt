package com.haroof.home

import com.haroof.data.FakeData
import com.haroof.data.repository.fake.FakeCoinsRepository
import com.haroof.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun stateIsInitiallyLoading() = runTest {
    val viewModel = HomeViewModel(FakeCoinsRepository())
    assertEquals(viewModel.uiState.value, HomeUiState.Loading)
  }

  @Test
  fun whenDataRefreshIsSuccessful_stateIsSuccessWithData() = runTest {
    val viewModel = HomeViewModel(FakeCoinsRepository())
    viewModel.refresh()
    assertEquals(viewModel.uiState.value, HomeUiState.Success(FakeData.COINS))
  }

  @Test
  fun whenDataRefreshFailed_stateIsError() = runTest {
    val viewModel = HomeViewModel(FakeCoinsRepository(true))
    viewModel.refresh()
    assertTrue(viewModel.uiState.value is HomeUiState.Error)
  }
}