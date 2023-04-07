package com.haroof.testing.data

import com.haroof.domain.model.SimpleCoin

object SimpleCoinTestData {

  val COIN_GOING_DOWN = SimpleCoin(
    "bitcoin",
    "Bitcoin",
    "btc",
    22409f,
    -1.32876f,
    1,
    "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
    listOf(8.0f, 1.0f, 8.0f, 7.5f, 8.0f, 5.0f, 5.0f)
  )

  val COIN_GOING_UP = SimpleCoin(
    "ethereum",
    "Ethereum",
    "eth",
    1567.86f,
    6.978393f,
    2,
    "https://assets.coingecko.com/coins/images/279/large/ethereum.png?1595348880",
    listOf(7.0f, 6.0f, 1.0f, 7.0f, 5.0f, 6.0f, 4.0f)
  )

  val COIN_STAYING_NEUTRAL = SimpleCoin(
    "tether",
    "Tether",
    "usdt",
    1f,
    0.0f,
    3,
    "https://assets.coingecko.com/coins/images/325/large/Tether.png?1668148663",
    listOf(5.0f, 7.0f, 3.4f, 6.0f, 2.0f, 8.0f, 9.0f)
  )

  //  list sorted by market cap rank
  val LIST = listOf(
    COIN_GOING_DOWN,
    COIN_GOING_UP,
    COIN_STAYING_NEUTRAL,
  )

  val GAINERS_AND_LOSERS = listOf(
    COIN_GOING_UP,
    COIN_GOING_DOWN,
    COIN_STAYING_NEUTRAL
  )
}