package com.haroof.market

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
import com.haroof.common.ui.EmptyListState
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.data.FakeData
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.market.MarketUiState.Empty
import com.haroof.market.MarketUiState.Error
import com.haroof.market.MarketUiState.Loading
import com.haroof.market.MarketUiState.Success
import com.haroof.market.R.string
import com.haroof.common.R as commonR

@Composable
fun MarketRoute(
  viewModel: MarketViewModel = hiltViewModel(),
  onNavigateToCoinDetail: (coinId: String) -> Unit,
  imageLoader: ImageLoader = LocalContext.current.imageLoader
) {
  val uiState by viewModel.uiState.collectAsState()

  MarketScreen(
    uiState = uiState,
    onSortChange = viewModel::sort,
    onNavigateToCoinDetail = onNavigateToCoinDetail,
    imageLoader = imageLoader
  )
}

@Composable
fun MarketScreen(
  uiState: MarketUiState,
  onSortChange: (sortBy: SortBy) -> Unit,
  onNavigateToCoinDetail: (String) -> Unit,
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
      Empty -> {
        EmptyListState(
          emptyStateMessageResId = string.market_empty_state_message
        )
      }
      is Success -> {
        MarketCoinsList(
          coins = uiState.coins,
          sortBy = uiState.sortBy,
          sortOrder = uiState.sortOrder,
          onSortChange = onSortChange,
          onNavigateToCoinDetail = onNavigateToCoinDetail,
          imageLoader = imageLoader,
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Loading() {
  CryptoHqTheme {
    MarketScreen(
      uiState = Loading,
      onSortChange = {},
      onNavigateToCoinDetail = {},
    )
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Error() {
  CryptoHqTheme {
    MarketScreen(
      uiState = Error(IllegalStateException()),
      onSortChange = {},
      onNavigateToCoinDetail = {},
    )
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Success() {
  CryptoHqTheme {
    MarketScreen(
      uiState = Success(FakeData.COINS),
      onSortChange = {},
      onNavigateToCoinDetail = {},
    )
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Success_EmptyState() {
  CryptoHqTheme {
    MarketScreen(
      uiState = Empty,
      onSortChange = {},
      onNavigateToCoinDetail = {},
    )
  }
}
