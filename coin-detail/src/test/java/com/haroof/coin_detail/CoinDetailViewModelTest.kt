package com.haroof.coin_detail

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
class CoinDetailViewModelTest {

  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun stateIsInitiallyLoading() {
    val viewModel = CoinDetailViewModel(
      FakeCoinsRepository(
        shouldThrowError = true,
        shouldReturnEmpty = false
      )
    )
    assertEquals(CoinDetailUiState.Loading, viewModel.uiState.value)
  }

  @Test
  fun whenDataFetchFailed_stateIsError() = runTest {
    val viewModel = CoinDetailViewModel(
      FakeCoinsRepository(
        shouldThrowError = true,
        shouldReturnEmpty = false
      )
    )
    assertTrue(viewModel.uiState.value is CoinDetailUiState.Error)
  }

  @Test
  fun whenDataFetchIsSuccessful_stateIsSuccess() = runTest {
    val viewModel = CoinDetailViewModel(FakeCoinsRepository())
    assertEquals(
      CoinDetailUiState.Success(FakeData.COINS.firstOrNull { it.id == "bitcoin" }),
      viewModel.uiState.value
    )
  }
}