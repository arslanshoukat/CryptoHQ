package com.haroof.common.util

import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import java.time.LocalDate

class DateAxisValueFormatter<Position : AxisPosition>(private val totalDays: Int) :
  AxisValueFormatter<Position> {

  override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
    val date = LocalDate.now().minusDays(totalDays - value.toLong())
    return "${date.dayOfMonth}/${date.monthValue}"
  }
}

class CurrencyAxisValueFormatter<Position : AxisPosition>(private val currency: String) :
  AxisValueFormatter<Position> {

  override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
    return "$currency$value"
  }
}