package com.haroof.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.haroof.data.FakeData
import com.haroof.designsystem.theme.CryptoHQTheme
import com.haroof.home.HomeUiState.Error
import com.haroof.home.HomeUiState.Loading
import com.haroof.home.HomeUiState.Success
import com.haroof.home.R.drawable
import com.haroof.home.R.string

@Composable
fun HomeRoute(viewModel: HomeViewModel = viewModel()) {
  val uiState by viewModel.uiState.collectAsState()

  HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(uiState: HomeUiState) {
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
      is Success -> {
        val contentDesc = stringResource(string.coins_list)
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = contentDesc }
        ) {
          itemsIndexed(
            key = { _, coin -> coin.id },
            items = uiState.coins
          ) { index, coin ->
            CoinListItem(coin = coin)
            if (index < uiState.coins.lastIndex) Divider(color = Color.LightGray, thickness = 1.dp)
          }
        }
      }
      is Error -> {
        ErrorMessageWithIcon(
          errorIconResId = drawable.ic_error_alert,
          errorMessageResId = string.error_message_data_fetch,
          contentDesc = stringResource(string.error_message),
        )
      }
    }

  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
  CryptoHQTheme {
    HomeScreen(Loading)
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenSuccessPreview() {
  CryptoHQTheme {
    HomeScreen(Success(FakeData.COINS))
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
  CryptoHQTheme {
    HomeScreen(Error(IllegalStateException()))
  }
}