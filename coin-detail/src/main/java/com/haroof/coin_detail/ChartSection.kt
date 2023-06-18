package com.haroof.coin_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ChipDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haroof.coin_detail.R.string
import com.haroof.common.model.TimeFilter
import com.haroof.common.util.CurrencyAxisValueFormatter
import com.haroof.common.util.rememberMarker
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.designsystem.theme.textLightBlack
import com.haroof.domain.model.MarketTrend
import com.haroof.domain.model.MarketTrend.DOWN
import com.haroof.domain.model.MarketTrend.NEUTRAL
import com.haroof.domain.model.MarketTrend.UP
import com.haroof.domain.sample_data.ChartEntrySampleData
import com.haroof.domain.sample_data.CurrencySampleData
import com.patrykandpatrick.vico.compose.axis.axisGuidelineComponent
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp.None
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
internal fun ChartSection(
  chartUiState: ChartUiState,
  currencyUnit: String,
  marketTrend: MarketTrend,
  onTimeFilterChanged: (TimeFilter) -> Unit = {},
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 0.dp)
  ) {
    TimeFilter.values().map {
      ChartFilterChip(
        title = it.title,
        selected = it == chartUiState.selectedTimeFilter,
        onClick = { onTimeFilterChanged(it) },
      )
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .height(256.dp)
      .fillMaxWidth()
      .background(MaterialTheme.colors.surface)
  ) {
    if (chartUiState.loading) {
      val contentDesc = stringResource(string.chart_loading_indicator_content_desc)
      CircularProgressIndicator(
        modifier = Modifier.semantics { contentDescription = contentDesc }
      )
    } else if (chartUiState.exception != null) {
      Text(
        text = stringResource(string.chart_data_fetch_error),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(16.dp)
      )
    } else if (chartUiState.chartData.isEmpty()) {
      Text(
        text = stringResource(string.empty_chart_data_message),
        style = MaterialTheme.typography.subtitle1,
        modifier = Modifier.padding(16.dp)
      )
    }

    val color = when (marketTrend) {
      NEUTRAL -> LocalTextStyle.current.color
      UP -> green
      DOWN -> red
    }
    val chartContentDesc = stringResource(string.chart_content_desc)
    Chart(
      marker = rememberMarker(),
      chart = lineChart(
        pointPosition = LineChart.PointPosition.Start,
        lines = listOf(
          lineSpec(
            lineColor = color,
            lineBackgroundShader = verticalGradient(
              arrayOf(color.copy(0.5f), color.copy(alpha = 0.2f)),
            ),
          )
        )
      ),
      startAxis = startAxis(
        tick = null,
        axis = null,
        guideline = axisGuidelineComponent(color = Color.LightGray),
        maxLabelCount = 3,
        horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
        verticalLabelPosition = VerticalAxis.VerticalLabelPosition.Bottom,
        label = axisLabelComponent(color = Color.Black, textSize = 10.sp),
        valueFormatter = CurrencyAxisValueFormatter(currencyUnit)
      ),
      model = entryModelOf(*chartUiState.chartData.map { it.value }.toTypedArray()),
      isZoomEnabled = false,
      chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
      autoScaleUp = None,
      modifier = Modifier
        .height(256.dp)
        .padding(vertical = 8.dp)
        .semantics { contentDescription = chartContentDesc }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ChartFilterChip(
  title: String,
  selected: Boolean,
  onClick: () -> Unit,
) {
  FilterChip(
    selected = selected,
    onClick = onClick,
    colors = ChipDefaults.filterChipColors(
      selectedBackgroundColor = MaterialTheme.colors.primary,
    ),
  ) {
    Text(
      text = title,
      color = if (selected) MaterialTheme.colors.onPrimary else textLightBlack
    )
  }
}

@Preview(showBackground = true)
@Composable
private fun ChartSectionPreview_Loading() {
  CryptoHqTheme {
    Column(
      Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)
    ) {
      ChartSection(
        chartUiState = ChartUiState(loading = true),
        currencyUnit = CurrencySampleData.USD.unit,
        marketTrend = UP,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ChartSectionPreview_Error() {
  CryptoHqTheme {
    Column(
      Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)
    ) {
      ChartSection(
        chartUiState = ChartUiState(exception = IllegalStateException("No internet connection!")),
        currencyUnit = CurrencySampleData.USD.unit,
        marketTrend = UP,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ChartSectionPreviewWithNoData() {
  CryptoHqTheme {
    Column(
      Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)
    ) {
      ChartSection(
        chartUiState = ChartUiState(chartData = emptyList()),
        currencyUnit = CurrencySampleData.USD.unit,
        marketTrend = UP,
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
private fun ChartSectionPreviewWithData() {
  CryptoHqTheme {
    Column(
      Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)
    ) {
      ChartSection(
        chartUiState = ChartUiState(chartData = ChartEntrySampleData.LIST),
        currencyUnit = CurrencySampleData.USD.unit,
        marketTrend = UP,
      )
    }
  }
}