package com.haroof.data

import com.haroof.data.model.Coin
import com.haroof.data.model.DetailedCoin
import com.haroof.data.model.Links

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
      "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
      listOf(8.0f, 1.0f, 8.0f, 7.5f, 8.0f, 5.0f, 5.0f)
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
      "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
      listOf(7.0f, 6.0f, 1.0f, 7.0f, 5.0f, 6.0f, 4.0f)
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
      "https://assets.coingecko.com/coins/images/325/large/Tether.png?1668148663",
      listOf(5.0f, 7.0f, 3.4f, 6.0f, 2.0f, 8.0f, 9.0f)
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
      "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
      listOf(7.0f, 6.0f, 1.0f, 7.0f, 5.0f, 6.0f, 4.0f)
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
      "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
      listOf(8.0f, 1.0f, 8.0f, 7.5f, 8.0f, 5.0f, 5.0f)
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
      "https://assets.coingecko.com/coins/images/325/large/Tether.png?1668148663",
      listOf(5.0f, 7.0f, 3.4f, 6.0f, 2.0f, 8.0f, 9.0f)
    ),
  )

  val WATCH_LIST_COIN_IDS = listOf("bitcoin", "ethereum")

  val DETAILED_COINS = listOf(
    DetailedCoin(
      "bitcoin",
      "Bitcoin",
      "btc",
      22409f,
      -297.4185f,
      -1.32876f,
      -1.32876f,
      -1.32876f,
      -1.32876f,
      426421161492,
      1,
      "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
      listOf(8.0f, 1.0f, 8.0f, 7.5f, 8.0f, 5.0f, 5.0f),
      20057f,
      23542f,
      0.513f,
      43061f,
      4123263662.0,
      4123263662.0,
      4123263662.0,
      Links("", "", "", ""),
      "",
    ),
    DetailedCoin(
      "ethereum",
      "Ethereum",
      "eth",
      1567.86f,
      0.44575f,
      6.978393f,
      3.533f,
      4.93f,
      13.643f,
      187751980959,
      2,
      "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
      listOf(7.0f, 6.0f, 1.0f, 7.0f, 5.0f, 6.0f, 4.0f),
      1510.0f,
      1592.14f,
      5f,
      1752.09f,
      23263662.0,
      23263662.0,
      23263662.0,
      Links("", "", "", ""),
      "",
    ),
    DetailedCoin(
      "tether",
      "Tether",
      "usdt",
      1f,
      0.00463f,
      0.0f,
      0.0f,
      0.0f,
      0.0f,
      71743402064,
      3,
      "https://assets.coingecko.com/coins/images/325/large/Tether.png?1668148663",
      listOf(5.0f, 7.0f, 3.4f, 6.0f, 2.0f, 8.0f, 9.0f),
      1f,
      1f,
      1f,
      1.0001214f,
      42413662.0,
      41363662.0,
      41233662.0,
      Links("", "", "", ""),
      "",
    ),
  )
}