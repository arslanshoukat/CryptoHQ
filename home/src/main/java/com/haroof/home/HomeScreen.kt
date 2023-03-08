package com.haroof.home

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.haroof.home.HomeUiState.Error
import com.haroof.home.HomeUiState.Loading
import com.haroof.home.HomeUiState.Success
import com.haroof.home.R.string

@Composable
fun HomeRoute(viewModel: HomeViewModel) {
  val uiState by viewModel.uiState.collectAsState()

  HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(uiState: HomeUiState) {
  when (uiState) {
    Loading -> {
      val loadingStringRes = stringResource(string.loading_indicator)
      CircularProgressIndicator(
        modifier = Modifier.semantics { contentDescription = loadingStringRes }
      )
    }
    is Success -> TODO()
    is Error -> TODO()
  }
}
