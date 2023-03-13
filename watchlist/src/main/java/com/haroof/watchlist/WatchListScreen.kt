package com.haroof.watchlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import com.haroof.watchlist.WatchListUiState.Loading
import com.haroof.common.R as commonR

@Composable
fun WatchListRoute(viewModel: WatchListViewModel = hiltViewModel()) {
  val uiState by viewModel.state.collectAsState()

  WatchListScreen(uiState = uiState)
}

@Composable
fun WatchListScreen(
  uiState: WatchListUiState,
  modifier: Modifier = Modifier
) {
  Box(modifier = modifier.fillMaxSize()) {

    when (uiState) {
      Loading -> {
        val contentDesc = stringResource(commonR.string.loading_indicator)
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .semantics { contentDescription = contentDesc }
        )
      }
    }
  }
}
