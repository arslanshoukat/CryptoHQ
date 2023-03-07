package com.haroof.home

import junit.framework.Assert.assertEquals
import org.junit.Test

class HomeViewModelTest {

  @Test
  fun stateIsInitiallyLoading() {
    val viewModel = HomeViewModel()
    assertEquals(viewModel.state.value, HomeUiState.Loading)
  }
}