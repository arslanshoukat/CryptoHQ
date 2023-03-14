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
    assertEquals(HomeUiState.Success(FakeData.COINS), viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshIsSuccessfulButEmpty_stateIsEmpty() = runTest {
    val viewModel = HomeViewModel(
      FakeCoinsRepository(
        shouldThrowError = false,
        shouldReturnEmpty = true
      )
    )
    assertEquals(HomeUiState.Empty, viewModel.uiState.value)
  }

  @Test
  fun whenDataRefreshFailed_stateIsError() = runTest {
    val viewModel = HomeViewModel(
      FakeCoinsRepository(
        shouldThrowError = true,
        shouldReturnEmpty = false
      )
    )
    assertTrue(viewModel.uiState.value is HomeUiState.Error)
  }
}