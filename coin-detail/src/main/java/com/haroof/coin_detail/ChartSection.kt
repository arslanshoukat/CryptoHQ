package com.haroof.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haroof.common.model.TimeFilter
import com.haroof.common.util.CurrencyAxisValueFormatter
import com.haroof.common.util.rememberMarker
import com.haroof.data.model.MarketTrend
import com.haroof.data.model.MarketTrend.DOWN
import com.haroof.data.model.MarketTrend.NEUTRAL
import com.haroof.data.model.MarketTrend.UP
import com.haroof.designsystem.theme.CryptoHqTheme
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.designsystem.theme.textLightBlack
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
  marketTrend: MarketTrend,
  selectedTimeFilter: TimeFilter,
  chartData: List<Double>,
  onTimeFilterChanged: (TimeFilter) -> Unit
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
        selected = it == selectedTimeFilter,
        onClick = { onTimeFilterChanged(it) },
      )
    }
  }

  Spacer(modifier = Modifier.height(8.dp))

  Surface {
    val color = when (marketTrend) {
      NEUTRAL -> LocalTextStyle.current.color
      UP -> green
      DOWN -> red
    }
    val marker = rememberMarker()
    Chart(
      marker = marker,
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
        valueFormatter = CurrencyAxisValueFormatter("$")  //  todo: pass selected currency
      ),
      model = entryModelOf(*chartData.toTypedArray()),
      isZoomEnabled = false,
      chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
      autoScaleUp = None,
      modifier = Modifier
        .height(256.dp)
        .padding(vertical = 16.dp)
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
fun ChartSectionPreview() {
  CryptoHqTheme {
    Column(
      Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp)
    ) {
      ChartSection(
        marketTrend = UP,
        selectedTimeFilter = TimeFilter.ONE_WEEK,
        chartData = listOf(21359.0, 28492.0, 22412.41, 25771.1, 22451.0, 24779.3, 23099.6),
        onTimeFilterChanged = {},
      )
    }
  }
}