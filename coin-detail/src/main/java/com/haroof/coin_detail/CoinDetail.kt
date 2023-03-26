package com.haroof.coin_detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.haroof.coin_detail.R.string
import com.haroof.common.extension.roundDecimal
import com.haroof.common.model.TimeFilter
import com.haroof.data.FakeData
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.MarketTrend.DOWN
import com.haroof.data.model.MarketTrend.NEUTRAL
import com.haroof.data.model.MarketTrend.UP
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.designsystem.theme.textLightBlack
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.chart.line.LineChart.PointPosition.Start
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp.None
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.haroof.common.R as commonR

@Composable
fun CoinDetail(
  coin: DetailedCoin,
  selectedTimeFilter: TimeFilter,
  onTimeFilterChanged: (TimeFilter) -> Unit,
  modifier: Modifier = Modifier
) {
  val contentDesc = stringResource(string.coin_detail_content_desc)
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .padding(16.dp)
      .verticalScroll(rememberScrollState())
      .semantics { contentDescription = contentDesc }
  ) {
    HeaderSection(coin)
    Spacer(modifier = Modifier.height(16.dp))
    ChartSection(
      coin = coin,
      selectedTimeFilter = selectedTimeFilter,
      onTimeFilterChanged = onTimeFilterChanged
    )
    Spacer(modifier = Modifier.height(16.dp))
    InfoSection(coin)
    Spacer(modifier = Modifier.height(16.dp))
    AboutSection(coin)
  }
}

@Composable
private fun HeaderSection(coin: DetailedCoin) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.fillMaxWidth()
  ) {
    Text(
      text = "$${coin.currentPrice}",
      style = MaterialTheme.typography.h5
    )
    Spacer(Modifier.width(12.dp))
    Surface(
      shape = CircleShape,
      color = green.copy(alpha = 0.2f),
      contentColor = green,
    ) {
      Text(
        text = "${coin.priceChangePercentage24h.roundDecimal(2)}%",
        style = MaterialTheme.typography.body2.copy(color = green),
        modifier = Modifier
          .padding(horizontal = 8.dp, vertical = 4.dp),
      )
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChartFilterChip(
  title: String,
  enabled: Boolean,
  onClick: () -> Unit,
) {
  Chip(
    enabled = enabled,
    onClick = onClick,
    colors = ChipDefaults.chipColors(
      backgroundColor = MaterialTheme.colors.primary,
    ),
  ) {
    Text(
      text = title,
      color = if (enabled) MaterialTheme.colors.onPrimary else textLightBlack
    )
  }
}

@Composable
private fun ChartSection(
  coin: DetailedCoin,
  selectedTimeFilter: TimeFilter,
  onTimeFilterChanged: (TimeFilter) -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()
  ) {
    TimeFilter.values().map {
      ChartFilterChip(
        title = it.title,
        enabled = it == selectedTimeFilter,
        onClick = { onTimeFilterChanged(it) },
      )
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  Card {
    Column(modifier = Modifier.padding(16.dp)) {
      val color = when (coin.marketTrend) {
        NEUTRAL -> LocalTextStyle.current.color
        UP -> green
        DOWN -> red
      }
      Chart(
        chart = lineChart(
          pointPosition = Start,
          lines = listOf(
            lineSpec(
              lineColor = color,
              lineBackgroundShader = verticalGradient(
                arrayOf(color.copy(0.5f), color.copy(alpha = 0.1f)),
              ),
            )
          )
        ),
        startAxis = startAxis(),
        bottomAxis = bottomAxis(),
        model = entryModelOf(*coin.sparklineIn7d.toTypedArray()),
        isZoomEnabled = false,
        chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
        autoScaleUp = None,
        modifier = Modifier
          .height(192.dp)
      )
    }
  }
}

@Composable
private fun InfoSection(
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
        title = stringResource(commonR.string.market_cap_rank),
        value = "#${coin.marketCapRank}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string.market_cap),
        value = "$${coin.marketCap}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string._24h_low),
        value = "$${coin.low24h}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string._24h_high),
        value = "$${coin.high24h}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string.all_time_low),
        value = "$${coin.allTimeLow}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string.all_time_high),
        value = "$${coin.allTimeHigh}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string.circulating_supply),
        value = "$${coin.circulatingSupply}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string.total_supply),
        value = "$${coin.totalSupply}"
      )
      Divider()
      InfoItem(
        title = stringResource(commonR.string.max_supply),
        value = "$${coin.maxSupply}"
      )
    }
  }
}

@Composable
fun InfoItem(
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

@Composable
fun AboutSection(
  coin: DetailedCoin,
  modifier: Modifier = Modifier
) {
  Text(
    text = "About",
    style = MaterialTheme.typography.h6
  )
  Spacer(modifier = Modifier.height(4.dp))

  Card(modifier = modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
      var expanded by remember {
        mutableStateOf(false)
      }

      if (coin.description.isNotBlank()) {
        Text(
          text = HtmlCompat.fromHtml(coin.description, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
          style = MaterialTheme.typography.body2,
          maxLines = if (expanded) Int.MAX_VALUE else 3,
          overflow = if (expanded) TextOverflow.Clip else TextOverflow.Ellipsis,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Icon(
          painter = painterResource(if (expanded) commonR.drawable.sharp_expand_less_24 else commonR.drawable.sharp_expand_more_24),
          contentDescription = "Expand icon",
          modifier = Modifier
            .clickable { expanded = !expanded }
            .size(32.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
      }

      Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
      ) {
        val context = LocalContext.current
        IconButton(onClick = { openUrl(context, coin.links.facebook) }) {
          Icon(
            painter = painterResource(id = commonR.drawable.facebook),
            contentDescription = "Facebook icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
          )
        }
        IconButton(onClick = { openUrl(context, coin.links.twitter) }) {
          Icon(
            painter = painterResource(id = commonR.drawable.twitter),
            contentDescription = "Twitter icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
          )
        }
        IconButton(onClick = { openUrl(context, coin.links.reddit) }) {
          Icon(
            painter = painterResource(id = commonR.drawable.reddit),
            contentDescription = "Reddit icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(32.dp)
          )
        }
      }
    }
  }
}

fun openUrl(context: Context, url: String) {
  try {
    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
  } catch (e: ActivityNotFoundException) {
    // todo: show error if no activity found
    Log.e("Coin Detail", e.localizedMessage, e)
  }
}

@Preview(showBackground = true)
@Composable
fun CoinDetailPreview() {
  CryptoHqTheme {
    Surface {
      CoinDetail(
        coin = FakeData.DETAILED_COINS.first(),
        selectedTimeFilter = TimeFilter.ONE_WEEK,
        onTimeFilterChanged = {}
      )
    }
  }
}