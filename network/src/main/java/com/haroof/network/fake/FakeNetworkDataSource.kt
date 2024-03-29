package com.haroof.network.fake

import android.util.Log
import com.haroof.network.NetworkDataSource
import com.haroof.network.model.ChartDataDto
import com.haroof.network.model.CoinDto
import com.haroof.network.model.DetailedCoinDto
import com.haroof.network.model.GetExchangeRatesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
class FakeNetworkDataSource @Inject constructor(
  private val json: Json,
  private val assetManager: FakeAssetManager,
  //  todo: inject dispatcher
) : NetworkDataSource {

  override suspend fun getCoins(
    vs_currency: String,
    ids: List<String>,
    sparkline: Boolean
  ): List<CoinDto> = withContext(Dispatchers.IO) {
    try {
      assetManager.open(COINS_ASSET)
        .use<InputStream, List<CoinDto>>(json::decodeFromStream)
        .filter {
          if (ids.isEmpty()) true // if no ids provided, return all
          else it.id in ids
        }
    } catch (e: Exception) {
      Log.e(TAG, e.message, e)
      emptyList()
    }
  }

  override suspend fun getDetailedCoin(id: String): DetailedCoinDto = withContext(Dispatchers.IO) {
    assetManager.open(DETAILED_COINS_ASSET)
      .use<InputStream, List<DetailedCoinDto>>(json::decodeFromStream)
      .first { it.id == id }
  }

  override suspend fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): ChartDataDto = withContext(Dispatchers.IO) {
    assetManager.open(MARKET_CHART_ASSET).use(json::decodeFromStream)
  }

  override suspend fun getExchangeRates(): GetExchangeRatesResponse = withContext(Dispatchers.IO) {
    assetManager.open(EXCHANGE_RATES_ASSET).use(json::decodeFromStream)
  }

  companion object {

    //  top 100 coins with sparkline
    private const val COINS_ASSET = "coins.json"

    //  bitcoin and ethereum coin details
    private const val DETAILED_COINS_ASSET = "detailed_coins.json"

    //  bitcoin 7d market chart
    private const val MARKET_CHART_ASSET = "market_chart.json"

    private const val EXCHANGE_RATES_ASSET = "exchange_rates.json"

    private const val TAG = "FakeNetworkDataSource"
  }
}