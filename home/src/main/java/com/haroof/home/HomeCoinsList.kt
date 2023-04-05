package com.haroof.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.haroof.home.R.string
import com.haroof.testing.data.SimpleCoinTestData

@Composable
internal fun HomeCoinsList(
  coins: List<SimpleCoin>,
  modifier: Modifier = Modifier,
  onNavigateToCoinDetail: (String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  val contentDesc = stringResource(string.coins_list)

  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(MaterialTheme.colors.surface)
      .semantics { contentDescription = contentDesc }
  ) {
    itemsIndexed(
      key = { _, coin -> coin.id },
      items = coins
    ) { index, coin ->
      HomeCoinListItem(
        coin = coin,
        onNavigateToCoinDetail = onNavigateToCoinDetail,
        imageLoader = imageLoader
      )
      if (index < coins.lastIndex) Divider(color = Color.LightGray, thickness = 1.dp)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HomeCoinsListPreview() {
  CryptoHqTheme {
    HomeCoinsList(
      coins = SimpleCoinTestData.LIST,
    )
  }
}