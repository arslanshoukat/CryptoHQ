package com.haroof.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
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
import com.haroof.data.FakeData
import com.haroof.data.model.Coin
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.home.R.string

@Composable
internal fun GainersAndLosers(
  coins: List<Coin>,
  onNavigateToCoinDetail: (String) -> Unit,
  imageLoader: ImageLoader,
  modifier: Modifier = Modifier
) {
  val contentDesc = stringResource(id = string.gainers_and_losers_list)
  LazyRow(
    contentPadding = PaddingValues(16.dp),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    modifier = modifier
      .fillMaxWidth()
      .semantics { contentDescription = contentDesc }
  ) {
    items(
      key = { coin -> coin.id },
      items = coins
    ) { coin ->
      CoinCard(
        coin = coin,
        onNavigateToCoinDetail = onNavigateToCoinDetail,
        imageLoader = imageLoader
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun GainersAndLosersPreview() {
  CryptoHqTheme {
    GainersAndLosers(
      coins = FakeData.COINS,
      onNavigateToCoinDetail = {},
      imageLoader = LocalContext.current.imageLoader
    )
  }
}