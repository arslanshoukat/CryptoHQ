package com.haroof.home

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
import com.haroof.common.R.string
import com.haroof.common.ui.EmptyListState
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.data.FakeData
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.home.HomeUiState.Empty
import com.haroof.home.HomeUiState.Error
import com.haroof.home.HomeUiState.Loading
import com.haroof.home.HomeUiState.Success

@Composable
fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
  val uiState by viewModel.uiState.collectAsState()

  HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(uiState: HomeUiState, imageLoader: ImageLoader = LocalContext.current.imageLoader) {
  Box(modifier = Modifier.fillMaxSize()) {

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
        //  todo: change empty state for home
        EmptyListState()
      }
      is Success -> {
        CoinsMarketList(
          coins = uiState.coins,
          imageLoader = imageLoader
        )
      }
    }

  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
  CryptoHqTheme {
    HomeScreen(Loading)
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenSuccessPreview() {
  CryptoHqTheme {
    HomeScreen(Success(FakeData.COINS))
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
  CryptoHqTheme {
    HomeScreen(Error(IllegalStateException()))
  }
}