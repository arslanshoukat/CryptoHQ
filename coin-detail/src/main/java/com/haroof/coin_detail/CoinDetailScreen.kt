package com.haroof.coin_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.haroof.coin_detail.CoinDetailUiState.Error
import com.haroof.coin_detail.CoinDetailUiState.Loading
import com.haroof.coin_detail.CoinDetailUiState.Success
import com.haroof.coin_detail.R.string
import com.haroof.common.ui.ErrorMessageWithIcon
import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.common.R as commonR

@Composable
fun CoinDetailRoute(
  viewModel: CoinDetailViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsState()
  CoinDetailScreen(uiState = uiState)
}

@Composable
fun CoinDetailScreen(
  uiState: CoinDetailUiState,
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
        CoinDetail(coin = uiState.coin)
      }
    }
  }
}

@Composable
fun CoinDetail(
  coin: Coin,
  modifier: Modifier = Modifier
) {
  val contentDesc = stringResource(string.coin_detail_content_desc)
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
      .semantics { contentDescription = contentDesc }
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(text = "$${coin.currentPrice}", style = MaterialTheme.typography.h6)
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = green.copy(alpha = 0.2f),
        contentColor = green,
      ) {
        Text(
          text = "${coin.priceChangePercentage24h}%",
          modifier = Modifier
            .padding(4.dp),
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CoinDetailPreview() {
  CryptoHqTheme {
    Surface {
      CoinDetail(coin = FakeData.COINS.first())
    }
  }
}