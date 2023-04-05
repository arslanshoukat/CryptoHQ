package com.haroof.market

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.haroof.common.ui.EmptyState
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.common.ui.SearchTopAppBar
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.market.MarketUiState.Empty
import com.haroof.market.MarketUiState.Error
import com.haroof.market.MarketUiState.Loading
import com.haroof.market.MarketUiState.Success
import com.haroof.market.R.string
import com.haroof.testing.data.SimpleCoinTestData
import com.haroof.common.R as commonR

@Composable
internal fun MarketRoute(
  viewModel: MarketViewModel = hiltViewModel(),
  onNavigateToCoinDetail: (coinId: String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
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
internal fun MarketScreen(
  uiState: MarketUiState,
  onSortChange: (sortBy: SortBy) -> Unit = {},
  onNavigateToCoinDetail: (String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  Column(modifier = Modifier.fillMaxSize()) {
    var value by remember {
      mutableStateOf("")
    }
    SearchTopAppBar(
      value = value,
      onValueChange = { value = it },
    )

    when (uiState) {
      Loading -> {
        val contentDesc = stringResource(commonR.string.loading_indicator)
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier.fillMaxSize()
        ) {
          CircularProgressIndicator(
            modifier = Modifier.semantics { contentDescription = contentDesc }
          )
        }
      }
      is Error -> {
        ErrorMessageWithIcon()
      }
      Empty -> {
        EmptyState(
          iconResId = commonR.drawable.no_results_illustration,
          titleResId = string.market_empty_state_title,
          subtitleResId = string.market_empty_state_subtitle,
          contentDescriptionResId = string.market_empty_state_content_description,
        )
      }
      is Success -> {
        Header(
          sortBy = uiState.sortBy,
          sortOrder = uiState.sortOrder,
          onSortChange = onSortChange,
        )

        MarketCoinsList(
          coins =
          if (value.isEmpty()) uiState.coins
          else uiState.coins.filter { it.name.contains(value, true) },
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
    MarketScreen(uiState = Loading)
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Error() {
  CryptoHqTheme {
    MarketScreen(uiState = Error(IllegalStateException()))
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Success() {
  CryptoHqTheme {
    MarketScreen(uiState = Success(SimpleCoinTestData.LIST))
  }
}

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview_Empty() {
  CryptoHqTheme {
    MarketScreen(uiState = Empty)
  }
}
