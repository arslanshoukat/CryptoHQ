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
import com.haroof.coin_detail.CoinDetailUiState.Error
import com.haroof.coin_detail.CoinDetailUiState.Loading
import com.haroof.coin_detail.CoinDetailUiState.Success
import com.haroof.common.model.TimeFilter
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.sample_data.WatchableDetailedCoinSampleData
import com.haroof.common.R as commonR

@Composable
internal fun CoinDetailRoute(
  viewModel: CoinDetailViewModel = hiltViewModel(),
  onBackPressed: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()
  CoinDetailScreen(
    uiState = uiState,
    onTimeFilterChanged = viewModel::updateTimeFilter,
    onToggleFavorite = viewModel::updateWatchListSelection,
    onBackPressed = onBackPressed,
  )
}

@Composable
internal fun CoinDetailScreen(
  uiState: CoinDetailUiState,
  onTimeFilterChanged: (timeFilter: TimeFilter) -> Unit = {},
  onToggleFavorite: (selected: Boolean) -> Unit = {},
  onBackPressed: () -> Unit = {},
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
          chartData = uiState.chartData,
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
fun CoinDetailScreenPreview_Loading() {
  CryptoHqTheme {
    Box {
      CoinDetailScreen(
        uiState = Loading
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CoinDetailScreenPreview_Error() {
  CryptoHqTheme {
    Box {
      CoinDetailScreen(
        uiState = Error(IllegalStateException()),
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CoinDetailScreenPreview_Success() {
  CryptoHqTheme {
    Box {
      CoinDetailScreen(
        uiState = Success(
          coin = WatchableDetailedCoinSampleData.WATCHED_COIN,
          selectedTimeFilter = TimeFilter.ONE_WEEK,
          chartData = listOf(21359.0, 28492.0, 22412.41, 25771.1, 22451.0, 24779.3, 23099.6),
        ),
      )
    }
  }
}