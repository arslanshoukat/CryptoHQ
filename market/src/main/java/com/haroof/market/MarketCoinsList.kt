package com.haroof.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.haroof.market.R.string
import com.haroof.testing.data.SimpleCoinTestData

@Composable
fun MarketCoinsList(
  coins: List<SimpleCoin>,
  modifier: Modifier = Modifier,
  onNavigateToCoinDetail: (String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  val contentDesc = stringResource(string.market_coins_list_content_desc)

  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 8.dp)
      .clip(MaterialTheme.shapes.small)
      .background(MaterialTheme.colors.surface)
      .semantics { contentDescription = contentDesc }
  ) {

    itemsIndexed(
      key = { _, coin -> coin.id },
      items = coins
    ) { index, coin ->
      MarketCoinsListItem(
        coin = coin,
        onNavigateToCoinDetail = onNavigateToCoinDetail,
        imageLoader = imageLoader
      )
      if (index < coins.lastIndex) {
        Row(modifier = modifier.fillMaxWidth()) {
          Spacer(modifier = Modifier.weight(0.1f))
          Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.weight(0.9f)
          )
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MarketCoinsListPreview() {
  CryptoHqTheme {
    MarketCoinsList(
      coins = SimpleCoinTestData.LIST,
    )
  }
}