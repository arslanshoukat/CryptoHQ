package com.haroof.data.repository

import android.util.Log
import com.haroof.data.model.ChartData
import com.haroof.data.model.Result
import com.haroof.data.model.toExternalModel
import com.haroof.network.NetworkDataSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultChartRepository @Inject constructor(
  //  todo: inject dispatchers here
  private val networkDataSource: NetworkDataSource
) : ChartRepository {

  override fun getChartData(
    id: String,
    vs_currency: String,
    days: String,
    interval: String
  ): Flow<Result<ChartData>> = flow {
    withContext(Dispatchers.IO) {
      emit(Result.Loading)
      try {
        val marketChart = networkDataSource.getChartData(
          id = id,
          vs_currency = vs_currency,
          days = days,
          interval = interval,
        ).toExternalModel()
        emit(Result.Success(marketChart))
      } catch (e: CancellationException) {
        throw e
      } catch (e: Exception) {
        Log.e(TAG, e.message, e)
        emit(Result.Error(e))
      }
    }
  }

  companion object {
    const val TAG = "DefaultChartRepository"
  }
}