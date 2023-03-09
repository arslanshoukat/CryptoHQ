package com.haroof.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.data.model.MarketTrend.DOWN
import com.haroof.data.model.MarketTrend.NEUTRAL
import com.haroof.data.model.MarketTrend.UP

@Composable
fun CoinListItem(
  coin: Coin,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .padding(16.dp)
      .fillMaxWidth()
  ) {
    Image(
      painter = painterResource(R.drawable.ic_default_coin),
      contentDescription = "coin icon",
      modifier = Modifier.size(48.dp)
    )

    Spacer(modifier = Modifier.width(16.dp))

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = coin.symbol.uppercase(),
        style = MaterialTheme.typography.subtitle1
      )
      Spacer(modifier = Modifier.height(8.dp))
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
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "${coin.priceChangePercentage24h}%",
        style = MaterialTheme.typography.body2,
        color = when (coin.marketTrend) {
          NEUTRAL -> LocalTextStyle.current.color
          UP -> Color.Green
          DOWN -> Color.Red
        }
      )
    }

  }
}

@Preview(showBackground = true)
@Composable
fun CoinListItemPreview() {
  com.haroof.designsystem.theme.CryptoHQTheme {
    CoinListItem(coin = FakeData.COINS.first())
  }
}