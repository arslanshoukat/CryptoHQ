package com.haroof.data

import com.haroof.data.model.Coin

object FakeData {

  val COINS = listOf(
    Coin(
      "bitcoin",
      "Bitcoin",
      "btc",
      22409f,
      -297.4185f,
      -1.32876f,
      426421161492,
      1,
      "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
    ),
    Coin(
      "ethereum",
      "Ethereum",
      "eth",
      1567.86f,
      0.44575f,
      6.978393f,
      187751980959,
      2,
      "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880"
    ),
    Coin(
      "tether",
      "Tether",
      "usdt",
      1f,
      0.00463f,
      0.00004634f,
      71743402064,
      3,
      "https://assets.coingecko.com/coins/images/325/large/Tether.png?1668148663"
    ),
  )

  val GAINERS_AND_LOSERS = listOf(
    Coin(
      "ethereum",
      "Ethereum",
      "eth",
      1567.86f,
      0.44575f,
      6.978393f,
      187751980959,
      2,
      "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880"
    ),
    Coin(
      "bitcoin",
      "Bitcoin",
      "btc",
      22409f,
      -297.4185f,
      -1.32876f,
      426421161492,
      1,
      "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579"
    ),
    Coin(
      "tether",
      "Tether",
      "usdt",
      1f,
      0.00463f,
      0.00004634f,
      71743402064,
      3,
      "https://assets.coingecko.com/coins/images/325/large/Tether.png?1668148663"
    ),
  )
}