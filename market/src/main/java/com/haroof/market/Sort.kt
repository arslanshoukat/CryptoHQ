package com.haroof.market

import com.haroof.market.SortOrder.ASCENDING
import com.haroof.market.SortOrder.DESCENDING

enum class SortBy {
  RANK,
  COIN,
  PRICE_CHANGE_PERCENTAGE,
  PRICE
}

enum class SortOrder {
  ASCENDING,
  DESCENDING,
}

fun SortOrder.toggle(): SortOrder {
  return when (this) {
    ASCENDING -> DESCENDING
    DESCENDING -> ASCENDING
  }
}
