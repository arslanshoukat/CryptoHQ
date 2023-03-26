package com.haroof.data.repository

import android.util.Log
import com.haroof.data.model.ChartData
import com.haroof.data.model.Result
import com.haroof.data.model.Result.Error
import com.haroof.data.model.Result.Success
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultChartRepository @Inject constructor(
  //  todo: inject dispatchers here
  private val networkDataSource: NetworkDataSource
) : ChartRepository {

  override suspend fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Result<ChartData> =
    withContext(Dispatchers.IO) {
      try {
        val marketChart = networkDataSource.getChartData(
          id = id,
          vs_currency = vs_currency,
          days = days,
          interval = interval,
        ).toExternalModel()
        Success(marketChart)
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        Error(e)
      }
    }

  companion object {
    const val TAG = "DefaultChartRepository"
  }
}