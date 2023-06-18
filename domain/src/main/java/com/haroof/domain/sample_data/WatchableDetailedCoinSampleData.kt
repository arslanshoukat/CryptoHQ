package com.haroof.domain.sample_data

import com.haroof.domain.model.WatchableDetailedCoin

object WatchableDetailedCoinSampleData {

  val WATCHED_COIN = WatchableDetailedCoin(
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
    20057f,
    23542f,
    0.513f,
    43061f,
    4123263662.0,
    4123263662.0,
    4123263662.0,
    "Bitcoin is the world’s first cryptocurrency which works on a completely decentralized network known as the blockchain. The blockchain network consists a link of blocks that are secured using cryptography and record all the transactions. Bitcoin was first presented to the world in 2009 by an anonymous identity known as Satoshi Nakamoto. As Bitcoin works on a decentralized network, it is completely free from the involvement of third-party financial institutions or central banks. The Bitcoin blockchain facilitates instant peer-to-peer transactions at minimum transactions fees required to maintain the network. The total number of Bitcoins is fixed at 21 million with its smallest unit being referred to as Satoshi. Each Satoshi represents a hundred millionth part of Bitcoin which means that 100,000,000 Santoshi = 1 BTC. Additional Bitcoins are generated by a process known as mining. Bitcoins are mined by professional miners solving complex computational equations. For each Bitcoin mined, the miners are rewarded with either more coins or transaction fees. The miners also validate all transactions on the Bitcoin network as well as look after the network security. Bitcoin can be exchanged with fiat currencies or other digital currencies. There are over 100,000 merchants and vendors accepting Bitcoin all over the world.",
    "https://bitcoin.org/",
    "https://www.facebook.com/bitcoin/",
    "https://www.twitter.com/bitcoin/",
    "https://www.reddit.com/r/bitcoin/",
    true,
    CurrencySampleData.USD.unit,
  )

  val NOT_WATCHED_COIN = WatchableDetailedCoin(
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
    1510.0f,
    1592.14f,
    5f,
    1752.09f,
    23263662.0,
    23263662.0,
    23263662.0,
    "Ethereum is a decentralized, open-source blockchain with smart contract functionality1. It’s a technology for building apps and organizations, holding assets, transacting and communicating without being controlled by a central authority2. Ether (Abbreviation: ETH; sign: Ξ) is the native cryptocurrency of the platform1. Ethereum was conceived in 2013 by programmer Vitalik Buterin.",
    "https://ethereum.org/",
    "https://www.facebook.com/ethereum/",
    "https://www.twitter.com/ethereum/",
    "https://www.reddit.com/r/ethereum/",
    false,
    CurrencySampleData.USD.unit,
  )

  val LIST = listOf(
    WATCHED_COIN,
    NOT_WATCHED_COIN,
    WatchableDetailedCoin(
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
      1f,
      1f,
      1f,
      1.0001214f,
      42413662.0,
      41363662.0,
      41233662.0,
      "Tether is a blockchain-enabled platform designed to facilitate the use of fiat currencies in a digital manner. You can store, send and receive digital tokens pegged to dollars, euros, and offshore Chinese yuan person-to-person, globally, instantly, and securely for a fraction of the cost of any alternative1.",
      "https://tether.to/en/",
      "https://www.facebook.com/tether.to/",
      "https://www.twitter.com/tether_to/",
      "https://www.reddit.com/r/tether/",
      false,
      CurrencySampleData.USD.unit,
    ),
  )
}


