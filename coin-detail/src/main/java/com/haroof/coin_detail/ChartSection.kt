package com.haroof.coin_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haroof.common.model.TimeFilter
import com.haroof.common.util.CurrencyAxisValueFormatter
import com.haroof.common.util.DateAxisValueFormatter
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.MarketTrend.DOWN
import com.haroof.data.model.MarketTrend.NEUTRAL
import com.haroof.data.model.MarketTrend.UP
import com.haroof.designsystem.theme.green
import com.haroof.designsystem.theme.red
import com.haroof.designsystem.theme.textLightBlack
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.chart.scale.AutoScaleUp.None
import com.patrykandpatrick.vico.core.entry.entryModelOf

@Composable
internal fun ChartSection(
  coin: DetailedCoin,
  selectedTimeFilter: TimeFilter,
  chartData: List<Double>,
  onTimeFilterChanged: (TimeFilter) -> Unit
) {
  Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = Modifier.fillMaxWidth()
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

  Card {
    Column(modifier = Modifier.padding(16.dp)) {
      val color = when (coin.marketTrend) {
        NEUTRAL -> LocalTextStyle.current.color
        UP -> green
        DOWN -> red
      }
      Chart(
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
          guideline = null,
          label = axisLabelComponent(textSize = 10.sp),
          valueFormatter = CurrencyAxisValueFormatter("$")  //  todo: pass selected currency
        ),
        bottomAxis = if (selectedTimeFilter == TimeFilter.ONE_DAY) null
        else bottomAxis(
          guideline = null,
          label = axisLabelComponent(textSize = 10.sp),
          valueFormatter = DateAxisValueFormatter(chartData.size)
        ),
        model = entryModelOf(*chartData.toTypedArray()),
        isZoomEnabled = false,
        chartScrollSpec = rememberChartScrollSpec(isScrollEnabled = false),
        autoScaleUp = None,
        modifier = Modifier
          .height(192.dp)
      )
    }
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