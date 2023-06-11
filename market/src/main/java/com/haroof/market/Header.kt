package com.haroof.market

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.haroof.market.R.drawable
import com.haroof.market.R.string
import com.haroof.market.SortBy.COIN
import com.haroof.market.SortBy.PRICE
import com.haroof.market.SortBy.PRICE_CHANGE_PERCENTAGE
import com.haroof.market.SortBy.RANK
import com.haroof.market.SortOrder.DESCENDING

@Composable
fun Header(
  sortBy: SortBy,
  sortOrder: SortOrder,
  modifier: Modifier = Modifier,
  onSortChanged: (sortBy: SortBy) -> Unit = {},
) {
  Card(
    elevation = 2.dp,
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = modifier
        .fillMaxWidth()
        .height(56.dp)
        .clip(MaterialTheme.shapes.small)
        .background(MaterialTheme.colors.surface)
        .padding(16.dp)
    ) {

      HeaderCell(
        title = stringResource(string.rank_title),
        weight = 0.1f,
        isSorted = sortBy == RANK,
        sortOrder = sortOrder,
        onClick = { onSortChanged(RANK) },
      )

      HeaderCell(
        title = stringResource(string.coin_title),
        weight = 0.35f,
        isSorted = sortBy == COIN,
        sortOrder = sortOrder,
        textAlign = TextAlign.Center,
        onClick = { onSortChanged(COIN) },
      )

      HeaderCell(
        title = stringResource(string.price_title),
        weight = 0.30f,
        isSorted = sortBy == PRICE,
        sortOrder = sortOrder,
        textAlign = TextAlign.End,
        onClick = { onSortChanged(PRICE) },
      )

      HeaderCell(
        title = stringResource(string.price_change_percentage_title),
        weight = 0.25f,
        isSorted = sortBy == PRICE_CHANGE_PERCENTAGE,
        sortOrder = sortOrder,
        textAlign = TextAlign.End,
        onClick = { onSortChanged(PRICE_CHANGE_PERCENTAGE) },
      )
    }
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
  contentDesc: String = title,
  onClick: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
      .weight(weight)
      .clickable(onClick = onClick)
      .semantics { contentDescription = contentDesc }
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
        contentDescription = title + " " + stringResource(id = if (sortOrder == DESCENDING) string.sort_descending_icon_content_desc else string.sort_ascending_icon_content_desc),
        modifier = Modifier.size(24.dp)
      )
    }
  }
}