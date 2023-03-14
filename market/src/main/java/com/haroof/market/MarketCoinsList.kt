package com.haroof.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.imageLoader
import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.market.R.string

@Composable
fun MarketCoinsList(
  coins: List<Coin>,
  imageLoader: ImageLoader,
  modifier: Modifier = Modifier
) {
  val contentDesc = stringResource(string.market_coins_list_content_desc)

  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clip(RoundedCornerShape(4.dp))
      .background(MaterialTheme.colors.surface)
      .semantics { contentDescription = contentDesc }
  ) {
    item {
      MarketCoinsListHeader()
    }

    itemsIndexed(
      key = { _, coin -> coin.id },
      items = coins
    ) { index, coin ->
      MarketCoinsListItem(
        coin = coin,
        imageLoader = imageLoader
      )
      if (index < coins.lastIndex) Divider(color = Color.LightGray, thickness = 1.dp)
    }
  }
}

@Composable
private fun MarketCoinsListHeader(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier
        .height(56.dp)
        .padding(vertical = 8.dp, horizontal = 16.dp)
        .fillMaxWidth()
    ) {

      Text(
        text = "Rank",
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.weight(0.15f)
      )

      Text(
        text = "Coin",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.Center,
        modifier = Modifier.weight(0.4f)
      )

      Text(
        text = "Price",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.End,
        modifier = Modifier.weight(0.25f)
      )

      Text(
        text = "24h %",
        style = MaterialTheme.typography.subtitle1,
        textAlign = TextAlign.End,
        modifier = Modifier.weight(0.2f)
      )

    }

    Divider(color = Color.LightGray, thickness = 1.dp)
  }
}

@Preview(showBackground = true)
@Composable
fun MarketCoinsListPreview() {
  CryptoHqTheme {
    MarketCoinsList(coins = FakeData.COINS, imageLoader = LocalContext.current.imageLoader)
  }
}