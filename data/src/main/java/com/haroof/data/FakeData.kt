package com.haroof.data

import com.haroof.data.model.Coin

object FakeData {

  val COINS = listOf(
    Coin("bitcoin", "Bitcoin", "btc", 22409f, -297.4185f, -1.32876f, 426421161492, 1),
    Coin("ethereum", "Ethereum", "eth", 1567.86f, -0.44575f, -6.978393f, 187751980959, 2),
    Coin("tether", "Tether", "usdt", 1f, 0.00463f, 0.00004634f, 71743402064, 3),
  )
}