package com.haroof.watchlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import com.haroof.common.R.drawable
import com.haroof.common.R.string
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.domain.model.MarketTrend.DOWN
import com.haroof.domain.model.MarketTrend.NEUTRAL
import com.haroof.domain.model.MarketTrend.UP
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.sample_data.SimpleCoinSampleData

@Composable
fun WatchListItem(
  coin: SimpleCoin,
  modifier: Modifier = Modifier,
  onNavigateToCoinDetail: (coinId: String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  Card(modifier = modifier.clickable { onNavigateToCoinDetail(coin.id) }) {
    Row(modifier = modifier.padding(16.dp)) {
      AsyncImage(
        model = coin.imageUrl,
        imageLoader = imageLoader,
        error = painterResource(id = drawable.ic_default_coin),
        fallback = painterResource(id = drawable.ic_default_coin),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(string.coin_icon_content_desc),
        modifier = Modifier.size(48.dp)
      )

      Spacer(modifier = Modifier.width(16.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = coin.symbol.uppercase(),
          style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = coin.name,
          style = MaterialTheme.typography.body2
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "$${coin.currentPrice}",
          style = MaterialTheme.typography.subtitle1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "${coin.priceChangePercentage24h}%",
          style = MaterialTheme.typography.body2,
          color = when (coin.marketTrend) {
            NEUTRAL -> LocalTextStyle.current.color
            UP -> green
            DOWN -> red
          }
        )
      }

    }
  }
}

@Preview(showBackground = true)
@Composable
fun WatchListItemUpPreview() {
  CryptoHqTheme {
    Surface(modifier = Modifier.padding(16.dp)) {
      WatchListItem(
        coin = SimpleCoinSampleData.COIN_GOING_UP,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun WatchListItemDownPreview() {
  CryptoHqTheme {
    Surface(modifier = Modifier.padding(16.dp)) {
      WatchListItem(
        coin = SimpleCoinSampleData.COIN_GOING_DOWN,
      )
    }
  }
}