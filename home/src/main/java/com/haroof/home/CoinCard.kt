package com.haroof.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import com.haroof.common.R.drawable
import com.haroof.common.R.string
import com.haroof.common.extension.roundDecimal
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.domain.model.MarketTrend.DOWN
import com.haroof.domain.model.MarketTrend.NEUTRAL
import com.haroof.domain.model.MarketTrend.UP
import com.haroof.domain.model.SimpleCoin
import com.haroof.domain.sample_data.SimpleCoinSampleData
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
internal fun CoinCard(
  coin: SimpleCoin,
  modifier: Modifier = Modifier,
  onNavigateToCoinDetail: (String) -> Unit = {},
  imageLoader: ImageLoader = LocalContext.current.imageLoader,
) {
  Card(modifier = modifier.size(width = 138.dp, height = 184.dp)) {
    ConstraintLayout(
      modifier = Modifier
        .clickable { onNavigateToCoinDetail(coin.id) }
        .semantics { contentDescription = coin.name }
    ) {
      val (image, symbol, name, price, priceChangePercentage, chart) = createRefs()

      AsyncImage(
        model = coin.imageUrl,
        imageLoader = imageLoader,
        placeholder = painterResource(id = drawable.ic_default_coin),
        error = painterResource(id = drawable.ic_default_coin),
        fallback = painterResource(id = drawable.ic_default_coin),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(string.coin_icon_content_desc),
        modifier = Modifier
          .size(32.dp)
          .constrainAs(image) {
            top.linkTo(symbol.top)
            bottom.linkTo(name.bottom)
            start.linkTo(parent.start, 16.dp)
          }
      )

      Text(
        text = coin.symbol.uppercase(),
        style = MaterialTheme.typography.body1,
        maxLines = 1,
        modifier = Modifier
          .constrainAs(symbol) {
            top.linkTo(parent.top, 16.dp)
            start.linkTo(image.end, 8.dp)
          }
      )

      Text(
        text = coin.name,
        style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
        maxLines = 1,
        modifier = Modifier
          .constrainAs(name) {
            top.linkTo(symbol.bottom)
            start.linkTo(symbol.start)
          }
      )

      Text(
        text = coin.currentPriceString,
        style = MaterialTheme.typography.body1,
        modifier = Modifier
          .constrainAs(price) {
            top.linkTo(name.bottom, 8.dp)
            start.linkTo(parent.start, 16.dp)
          }
      )

      val color = when (coin.marketTrend) {
        NEUTRAL -> LocalTextStyle.current.color
        UP -> green
        DOWN -> red
      }
      Text(
        text = "${coin.priceChangePercentage24h.roundDecimal(2)}%",
        style = MaterialTheme.typography.body2.copy(fontSize = 12.sp),
        color = color,
        modifier = Modifier
          .constrainAs(priceChangePercentage) {
            top.linkTo(price.bottom)
            start.linkTo(price.start)
          }
      )

      Chart(
        chart = lineChart(
          pointPosition = LineChart.PointPosition.Start,
          lines = listOf(
            lineSpec(
              lineColor = color,
              lineBackgroundShader = verticalGradient(
                arrayOf(color.copy(0.5f), color.copy(alpha = 0.1f)),
              ),
            )
          )
        ),
        model = entryModelOf(*coin.sparklineIn7d.toTypedArray()),
        isZoomEnabled = false,
        chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
        autoScaleUp = AutoScaleUp.None,
        modifier = Modifier
          .height(72.dp)
          .constrainAs(chart) {
            bottom.linkTo(parent.bottom)
            linkTo(start = parent.start, end = parent.end)
            width = Dimension.fillToConstraints
          }
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun CoinCardPreview() {
  CryptoHqTheme {
    Box(
      modifier = Modifier
        .padding(16.dp)
        .background(Color.LightGray)
    ) {
      CoinCard(
        coin = SimpleCoinSampleData.LIST.first(),
      )
    }
  }
}