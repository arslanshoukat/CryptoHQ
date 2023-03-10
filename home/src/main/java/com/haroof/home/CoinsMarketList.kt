package com.haroof.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.haroof.data.model.Coin
import com.haroof.home.R.string

@Composable
fun CoinsMarketList(
  coins: List<Coin>,
  imageLoader: ImageLoader,
  modifier: Modifier = Modifier
) {
  val contentDesc = stringResource(string.coins_list)

  Surface(
    modifier = modifier
      .padding(16.dp)
      .clip(RoundedCornerShape(16.dp))
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .semantics { contentDescription = contentDesc }
    ) {
      itemsIndexed(
        key = { _, coin -> coin.id },
        items = coins
      ) { index, coin ->
        CoinListItem(
          coin = coin,
          imageLoader = imageLoader
        )
        if (index < coins.lastIndex) Divider(color = Color.LightGray, thickness = 1.dp)
      }
    }
  }
}