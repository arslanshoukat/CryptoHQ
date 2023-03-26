package com.haroof.common.model

enum class TimeFilter(val title: String, val days: String, val interval: String) {
  ONE_DAY("24H", "1", ""),
  ONE_WEEK("1W", "7", "daily"),
  ONE_MONTH("1M", "30", "daily"),
  THREE_MONTH("3M", "90", "daily"),
  ONE_YEAR("1Y", "365", "daily"),
  MAX("Max", "max", "daily"),
}