package com.haroof.coin_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.imageLoader
import com.haroof.coin_detail.CoinDetailUiState.Error
import com.haroof.coin_detail.CoinDetailUiState.Loading
import com.haroof.coin_detail.CoinDetailUiState.Success
import com.haroof.common.model.TimeFilter
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.common.R as commonR

@Composable
fun CoinDetailRoute(
  viewModel: CoinDetailViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState()
  CoinDetailScreen(
    uiState = uiState,
    onTimeFilterChanged = viewModel::updateTimeFilter
  )
}

@Composable
fun CoinDetailScreen(
  uiState: CoinDetailUiState,
  onTimeFilterChanged: (TimeFilter) -> Unit,
  imageLoader: ImageLoader = LocalContext.current.imageLoader
) {
  Box(modifier = Modifier.fillMaxSize()) {
    when (uiState) {
      Loading -> {
        val contentDesc = stringResource(commonR.string.loading_indicator)
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .semantics { contentDescription = contentDesc }
        )
      }
      is Error -> {
        ErrorMessageWithIcon()
      }
      is Success -> {
        CoinDetail(
          coin = uiState.coin,
          selectedTimeFilter = uiState.selectedTimeFilter,
          onTimeFilterChanged = onTimeFilterChanged
        )
      }
    }
  }
}