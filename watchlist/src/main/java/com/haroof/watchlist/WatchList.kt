package com.haroof.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.imageLoader
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.domain.model.SimpleCoin
import com.haroof.testing.data.SimpleCoinTestData
import com.haroof.watchlist.R.string

@Composable
fun WatchList(
  coins: List<SimpleCoin>,
  modifier: Modifier = Modifier,
  onNavigateToCoinDetail: (coinId: String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  val contentDesc = stringResource(string.watch_list_coins)

  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    modifier = modifier
      .fillMaxWidth()
      .semantics { contentDescription = contentDesc }
  ) {
    items(
      key = { coin -> coin.id },
      items = coins
    ) { coin ->
      WatchListItem(
        coin = coin,
        onNavigateToCoinDetail = onNavigateToCoinDetail,
        imageLoader = imageLoader
      )
    }
  }
}

@Preview
@Composable
fun WatchListPreview() {
  CryptoHqTheme {
    WatchList(
      coins = SimpleCoinTestData.LIST,
    )
  }
}