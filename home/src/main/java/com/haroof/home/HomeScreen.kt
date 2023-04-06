package com.haroof.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.imageLoader
import com.haroof.common.ui.EmptyListState
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.sample_data.SimpleCoinSampleData
import com.haroof.home.HomeUiState.Empty
import com.haroof.home.HomeUiState.Error
import com.haroof.home.HomeUiState.Loading
import com.haroof.home.HomeUiState.Success
import com.haroof.home.R.string
import com.haroof.common.R as commonR

@Composable
internal fun HomeRoute(
  viewModel: HomeViewModel = hiltViewModel(),
  onNavigateToCoinDetail: (coinId: String) -> Unit = {},
  onNavigateToMarket: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()

  HomeScreen(
    uiState = uiState,
    onNavigateToCoinDetail = onNavigateToCoinDetail,
    onNavigateToMarket = onNavigateToMarket,
  )
}

@Composable
internal fun HomeScreen(
  uiState: HomeUiState,
  onNavigateToCoinDetail: (String) -> Unit = {},
  onNavigateToMarket: () -> Unit = {},
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
        //  todo: change empty state for home
        EmptyListState()
      }
      is Success -> {
        Column(
          modifier = Modifier.fillMaxSize()
        ) {
          Spacer(modifier = Modifier.height(32.dp))
          Row {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
              text = stringResource(string.gainers_and_losers),
              style = MaterialTheme.typography.h6
            )
          }
          GainersAndLosers(
            coins = uiState.gainersAndLosers,
            onNavigateToCoinDetail = onNavigateToCoinDetail,
            imageLoader = imageLoader
          )

          Spacer(modifier = Modifier.height(16.dp))

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(
              text = stringResource(string.market),
              style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onNavigateToMarket) {
              Text(text = stringResource(string.view_all))
            }
            Spacer(modifier = Modifier.width(16.dp))
          }
          HomeCoinsList(
            coins = uiState.marketCoins,
            onNavigateToCoinDetail = onNavigateToCoinDetail,
            imageLoader = imageLoader
          )
        }
      }
    }

  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
  CryptoHqTheme {
    HomeScreen(
      uiState = Loading,
    )
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenSuccessPreview() {
  CryptoHqTheme {
    HomeScreen(
      uiState = Success(
        gainersAndLosers = SimpleCoinSampleData.LIST,
        marketCoins = SimpleCoinSampleData.LIST,
      ),
    )
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
  CryptoHqTheme {
    HomeScreen(
      uiState = Error(IllegalStateException()),
    )
  }
}