package com.haroof.market

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import com.haroof.common.extension.roundDecimal
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.domain.model.MarketTrend.DOWN
import com.haroof.domain.model.MarketTrend.NEUTRAL
import com.haroof.domain.model.MarketTrend.UP
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.sample_data.SimpleCoinSampleData
import com.haroof.common.R as commonR

@Composable
fun MarketCoinsListItem(
  coin: SimpleCoin,
  modifier: Modifier = Modifier,
  onNavigateToCoinDetail: (String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .height(56.dp)
      .fillMaxWidth()
      .clickable { onNavigateToCoinDetail(coin.id) }
      .padding(vertical = 8.dp, horizontal = 16.dp)
  ) {

    Text(
      text = "${coin.marketCapRank}",
      modifier = Modifier.weight(0.1f),
    )

    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(0.65f)
    ) {
      AsyncImage(
        model = coin.imageUrl,
        imageLoader = imageLoader,
        error = painterResource(id = commonR.drawable.ic_default_coin),
        fallback = painterResource(id = commonR.drawable.ic_default_coin),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(commonR.string.coin_icon_content_desc),
        modifier = Modifier.size(24.dp)
      )

      Spacer(modifier = Modifier.width(16.dp))

      Text(
        text = coin.symbol.uppercase(),
        style = MaterialTheme.typography.body1
      )

      Spacer(modifier = Modifier.width(8.dp))

      Text(
        text = coin.currentPriceString,
        style = MaterialTheme.typography.body1,
        maxLines = 1,
        textAlign = TextAlign.End,
        modifier = Modifier.weight(1f)
      )
    }

    Text(
      text = "${coin.priceChangePercentage24h.roundDecimal(2)}%",
      style = MaterialTheme.typography.body1,
      textAlign = TextAlign.End,
      color = when (coin.marketTrend) {
        NEUTRAL -> LocalTextStyle.current.color
        UP -> green
        DOWN -> red
      },
      modifier = Modifier.weight(0.25f)
    )

  }
}

@Preview(showBackground = true)
@Composable
fun MarketCoinsListItemUpPreview() {
  CryptoHqTheme {
    MarketCoinsListItem(
      coin = SimpleCoinSampleData.COIN_GOING_UP,
    )
  }
}

@Preview(showBackground = true)
@Composable
fun MarketCoinsListItemDownPreview() {
  CryptoHqTheme {
    MarketCoinsListItem(
      coin = SimpleCoinSampleData.COIN_GOING_DOWN,
    )
  }
}