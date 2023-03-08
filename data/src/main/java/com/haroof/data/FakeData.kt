package com.haroof.data

import com.haroof.data.model.Coin

object FakeData {

  val COINS = listOf(
    Coin("bitcoin", "Bitcoin", "btc", 22409f),
    Coin("ethereum", "Ethereum", "eth", 1567.86f),
    Coin("tether", "Tether", "usdt", 1f),
  )
}