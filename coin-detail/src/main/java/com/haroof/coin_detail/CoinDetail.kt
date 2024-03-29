package com.haroof.coin_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.imageLoader
import com.haroof.coin_detail.R.string
import com.haroof.common.model.TimeFilter
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.model.WatchableDetailedCoin
import com.haroof.domain.sample_data.ChartEntrySampleData
import com.haroof.domain.sample_data.WatchableDetailedCoinSampleData

@Composable
internal fun CoinDetail(
  coin: WatchableDetailedCoin,
  chartUiState: ChartUiState,
  modifier: Modifier = Modifier,
  onTimeFilterChanged: (timeFilter: TimeFilter) -> Unit = {},
  onToggleFavorite: (selected: Boolean) -> Unit = {},
  onBackPressed: () -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  val contentDesc = stringResource(string.coin_detail_content_desc)
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
      .semantics { contentDescription = contentDesc }
  ) {
    HeaderSection(
      coin = coin,
      onBackPressed = onBackPressed,
      onToggleFavorite = onToggleFavorite,
      imageLoader = imageLoader,
    )
    Spacer(modifier = Modifier.height(16.dp))
    ChartSection(
      chartUiState = chartUiState,
      currencyUnit = coin.currencyUnit,
      marketTrend = coin.marketTrend,
      onTimeFilterChanged = onTimeFilterChanged
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoSection(coin)
    Spacer(modifier = Modifier.height(16.dp))
    AboutSection(coin)
    Spacer(modifier = Modifier.height(16.dp))
  }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailPreview() {
  CryptoHqTheme {
    Box {
      CoinDetail(
        coin = WatchableDetailedCoinSampleData.WATCHED_COIN,
        chartUiState = ChartUiState(chartData = ChartEntrySampleData.LIST),
      )
    }
  }
}