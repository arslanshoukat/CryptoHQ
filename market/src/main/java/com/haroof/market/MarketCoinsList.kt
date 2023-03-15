package com.haroof.market

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.haroof.market.R.drawable
import com.haroof.market.R.string
import com.haroof.market.SortOrder.DESCENDING

@Composable
fun MarketCoinsList(
  coins: List<Coin>,
  sortBy: SortBy,
  sortOrder: SortOrder,
  onSortChange: (sortBy: SortBy) -> Unit,
  imageLoader: ImageLoader,
  modifier: Modifier = Modifier
) {
  val contentDesc = stringResource(string.market_coins_list_content_desc)

  LazyColumn(
    modifier = modifier
      .fillMaxWidth()
      .padding(16.dp)
      .clip(MaterialTheme.shapes.small)
      .background(MaterialTheme.colors.surface)
      .border(BorderStroke(1.dp, Color.LightGray), MaterialTheme.shapes.small)
      .semantics { contentDescription = contentDesc }
  ) {
    item {
      Header(
        sortBy = sortBy,
        sortOrder = sortOrder,
        onSortChange = onSortChange,
      )
    }

    itemsIndexed(
      key = { _, coin -> coin.id },
      items = coins
    ) { index, coin ->
      MarketCoinsListItem(
        coin = coin,
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

@Composable
private fun Header(
  sortBy: SortBy,
  sortOrder: SortOrder,
  onSortChange: (sortBy: SortBy) -> Unit,
  modifier: Modifier = Modifier
) {
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

      HeaderCell(
        title = "#",
        weight = 0.1f,
        isSorted = sortBy == SortBy.RANK,
        sortOrder = sortOrder,
        onClick = { onSortChange(SortBy.RANK) },
      )

      HeaderCell(
        title = "Coin",
        weight = 0.4f,
        isSorted = sortBy == SortBy.COIN,
        sortOrder = sortOrder,
        textAlign = TextAlign.Center,
        onClick = { onSortChange(SortBy.COIN) },
      )

      HeaderCell(
        title = "Price",
        weight = 0.25f,
        isSorted = sortBy == SortBy.PRICE,
        sortOrder = sortOrder,
        textAlign = TextAlign.End,
        onClick = { onSortChange(SortBy.PRICE) },
      )

      HeaderCell(
        title = "24h %",
        weight = 0.25f,
        isSorted = sortBy == SortBy.PRICE_CHANGE_PERCENTAGE,
        sortOrder = sortOrder,
        textAlign = TextAlign.End,
        onClick = { onSortChange(SortBy.PRICE_CHANGE_PERCENTAGE) },
      )
    }

    Divider(color = Color.LightGray, thickness = 1.dp)
  }
}

@Composable
private fun RowScope.HeaderCell(
  title: String,
  weight: Float,
  isSorted: Boolean,
  sortOrder: SortOrder,
  modifier: Modifier = Modifier,
  textAlign: TextAlign? = null,
  onClick: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .weight(weight)
      .clickable(onClick = onClick)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.subtitle2,
      textAlign = textAlign,
      modifier = Modifier.weight(1f)
    )

    if (isSorted) {
      Icon(
        painter = painterResource(id = if (sortOrder == DESCENDING) drawable.sharp_arrow_down_24 else drawable.sharp_arrow_up_24),
        contentDescription = stringResource(string.sort_icon_content_desc),
        modifier = Modifier.size(24.dp)
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MarketCoinsListPreview() {
  CryptoHqTheme {
    MarketCoinsList(
      coins = FakeData.COINS,
      imageLoader = LocalContext.current.imageLoader,
      sortBy = SortBy.RANK,
      sortOrder = SortOrder.ASCENDING,
      onSortChange = {},
    )
  }
}