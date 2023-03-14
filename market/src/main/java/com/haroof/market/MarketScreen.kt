package com.haroof.market

import androidx.compose.foundation.layout.Box
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
import com.haroof.common.R.string
import com.haroof.common.ui.EmptyListState
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.market.MarketUiState.Empty
import com.haroof.market.MarketUiState.Error
import com.haroof.market.MarketUiState.Loading
import com.haroof.market.MarketUiState.Success

@Composable
fun MarketRoute(viewModel: MarketViewModel = hiltViewModel()) {
  val uiState by viewModel.uiState.collectAsState()

  MarketScreen(uiState = uiState)
}

@Composable
fun MarketScreen(
  uiState: MarketUiState,
  imageLoader: ImageLoader = LocalContext.current.imageLoader
) {
  Box(modifier = Modifier) {

    when (uiState) {
      Loading -> {
        val contentDesc = stringResource(string.loading_indicator)
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
        EmptyListState()
      }
      is Success -> {
        MarketCoinsList(
          coins = uiState.coins,
          imageLoader = imageLoader
        )
      }
    }
  }
}
