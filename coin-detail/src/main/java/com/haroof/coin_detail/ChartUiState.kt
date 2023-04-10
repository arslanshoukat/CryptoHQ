package com.haroof.coin_detail

import com.haroof.common.model.TimeFilter
import com.haroof.domain.model.ChartEntry

data class ChartUiState(
  val loading: Boolean = false,
  val exception: Throwable? = null,
  val selectedTimeFilter: TimeFilter = TimeFilter.ONE_WEEK,
  val chartData: List<ChartEntry> = emptyList(),
)