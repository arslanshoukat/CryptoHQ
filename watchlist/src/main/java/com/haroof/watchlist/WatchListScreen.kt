package com.haroof.watchlist

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
import com.haroof.common.ui.EmptyState
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.sample_data.SimpleCoinSampleData
import com.haroof.watchlist.R.string
import com.haroof.watchlist.WatchListUiState.Empty
import com.haroof.watchlist.WatchListUiState.Error
import com.haroof.watchlist.WatchListUiState.Loading
import com.haroof.watchlist.WatchListUiState.Success
import com.haroof.common.R as commonR

@Composable
internal fun WatchListRoute(
  viewModel: WatchListViewModel = hiltViewModel(),
  onNavigateToCoinDetail: (coinId: String) -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()

  WatchListScreen(
    uiState = uiState,
    onNavigateToCoinDetail = onNavigateToCoinDetail,
  )
}

@Composable
internal fun WatchListScreen(
  uiState: WatchListUiState,
  onNavigateToCoinDetail: (String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
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
        EmptyState(
          iconResId = commonR.drawable.no_favorite_illustration,
          titleResId = string.watch_list_empty_state_title,
          subtitleResId = string.watch_list_empty_state_subtitle,
          contentDescriptionResId = string.watch_list_empty_state_content_description,
        )
      }
      is Success -> {
        WatchList(
          coins = uiState.data,
          onNavigateToCoinDetail = onNavigateToCoinDetail,
          imageLoader = imageLoader
        )
      }

    }
  }
}

@Preview(showBackground = true)
@Composable
fun WatchListScreenPreview_Loading() {
  CryptoHqTheme {
    WatchListScreen(
      uiState = Loading,
    )
  }
}

@Preview(showBackground = true)
@Composable
fun WatchListScreenPreview_Error() {
  CryptoHqTheme {
    WatchListScreen(
      uiState = Error(IllegalStateException()),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun WatchListScreenPreview_Success() {
  CryptoHqTheme {
    WatchListScreen(
      uiState = Success(SimpleCoinSampleData.LIST),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun WatchListScreenPreview_Empty() {
  CryptoHqTheme {
    WatchListScreen(
      uiState = Empty,
    )
  }
}
