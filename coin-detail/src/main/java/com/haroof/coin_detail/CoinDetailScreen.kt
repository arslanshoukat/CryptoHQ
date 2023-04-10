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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.imageLoader
import com.haroof.common.model.TimeFilter
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.sample_data.ChartEntrySampleData
import com.haroof.domain.sample_data.WatchableDetailedCoinSampleData
import com.haroof.common.R as commonR

@Composable
internal fun CoinDetailRoute(
  viewModel: CoinDetailViewModel = hiltViewModel(),
  onBackPressed: () -> Unit = {},
) {
  val coinDetailUiState by viewModel.coinDetailUiState.collectAsState()
  val chartUiState by viewModel.chartUiState.collectAsState()

  CoinDetailScreen(
    coinDetailUiState = coinDetailUiState,
    chartUiState = chartUiState,
    onTimeFilterChanged = viewModel::updateTimeFilter,
    onToggleFavorite = viewModel::updateWatchListSelection,
    onBackPressed = onBackPressed,
  )
}

@Composable
internal fun CoinDetailScreen(
  coinDetailUiState: CoinDetailUiState,
  chartUiState: ChartUiState,
  onTimeFilterChanged: (timeFilter: TimeFilter) -> Unit = {},
  onToggleFavorite: (selected: Boolean) -> Unit = {},
  onBackPressed: () -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader
) {
  Box(modifier = Modifier.fillMaxSize()) {
    when (coinDetailUiState) {
      CoinDetailUiState.Loading -> {
        val contentDesc = stringResource(commonR.string.loading_indicator)
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .semantics { contentDescription = contentDesc }
        )
      }
      is CoinDetailUiState.Error -> {
        ErrorMessageWithIcon()
      }
      is CoinDetailUiState.Success -> {
        CoinDetail(
          coin = coinDetailUiState.coin,
          chartUiState = chartUiState,
          onTimeFilterChanged = onTimeFilterChanged,
          onToggleFavorite = onToggleFavorite,
          onBackPressed = onBackPressed,
          imageLoader = imageLoader,
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailScreenPreview_Loading() {
  CryptoHqTheme {
    Box {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Loading,
        chartUiState = ChartUiState(loading = true),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailScreenPreview_Error() {
  CryptoHqTheme {
    Box {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Error(IllegalStateException()),
        chartUiState = ChartUiState(loading = true),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailScreenPreview_Success() {
  CryptoHqTheme {
    Box {
      CoinDetailScreen(
        coinDetailUiState = CoinDetailUiState.Success(
          coin = WatchableDetailedCoinSampleData.WATCHED_COIN,
        ),
        chartUiState = ChartUiState(
          chartData = ChartEntrySampleData.LIST,
        ),
      )
    }
  }
}