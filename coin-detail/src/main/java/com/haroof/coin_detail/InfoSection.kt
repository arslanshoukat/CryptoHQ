package com.haroof.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.haroof.common.R.string
import com.haroof.data.model.DetailedCoin

@Composable
internal fun InfoSection(
  coin: DetailedCoin,
  modifier: Modifier = Modifier
) {
  Text(
    text = "Stats",
    style = MaterialTheme.typography.h6
  )
  Spacer(modifier = Modifier.height(4.dp))

  Card(modifier = modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
      InfoItem(
        title = stringResource(string.market_cap_rank),
        value = "#${coin.marketCapRank}"
      )
      Divider()
      InfoItem(
        title = stringResource(string.market_cap),
        value = "$${coin.marketCap}"
      )
      Divider()
      InfoItem(
        title = stringResource(string._24h_low),
        value = "$${coin.low24h}"
      )
      Divider()
      InfoItem(
        title = stringResource(string._24h_high),
        value = "$${coin.high24h}"
      )
      Divider()
      InfoItem(
        title = stringResource(string.all_time_low),
        value = "$${coin.allTimeLow}"
      )
      Divider()
      InfoItem(
        title = stringResource(string.all_time_high),
        value = "$${coin.allTimeHigh}"
      )
      Divider()
      InfoItem(
        title = stringResource(string.circulating_supply),
        value = "$${coin.circulatingSupply}"
      )
      Divider()
      InfoItem(
        title = stringResource(string.total_supply),
        value = "$${coin.totalSupply}"
      )
      Divider()
      InfoItem(
        title = stringResource(string.max_supply),
        value = "$${coin.maxSupply}"
      )
    }
  }
}

@Composable
internal fun InfoItem(
  title: String,
  value: String,
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 0.dp, vertical = 8.dp)
  ) {
    Text(text = title, style = MaterialTheme.typography.body2)

    Text(text = value, style = MaterialTheme.typography.body1)
  }
}