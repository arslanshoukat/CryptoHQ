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
import com.haroof.data.FakeData
import com.haroof.data.model.DetailedCoin
import com.haroof.designsystem.theme.CryptoHqTheme

@Composable
internal fun CoinDetail(
  coin: DetailedCoin,
  selectedTimeFilter: TimeFilter,
  chartData: List<Double>,
  onTimeFilterChanged: (TimeFilter) -> Unit,
  onBackPressed: () -> Unit = {},
  modifier: Modifier = Modifier,
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
      imageLoader = imageLoader,
    )
    Spacer(modifier = Modifier.height(16.dp))
    ChartSection(
      coin = coin,
      selectedTimeFilter = selectedTimeFilter,
      chartData = chartData,
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
internal fun CoinDetailPreview() {
  CryptoHqTheme {
    Box {
      CoinDetail(
        coin = FakeData.DETAILED_COINS.first(),
        selectedTimeFilter = TimeFilter.ONE_WEEK,
        chartData = listOf(21359.0, 28492.0, 22412.41, 25771.1, 22451.0, 24779.3, 23099.6),
        onTimeFilterChanged = {}
      )
    }
  }
}