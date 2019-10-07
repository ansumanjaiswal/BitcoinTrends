package com.jaiswal.bitcointrends.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jaiswal.bitcointrends.ChartDataBase
import com.jaiswal.bitcointrends.ViewModelFactory.Companion.application
import com.jaiswal.bitcointrends.models.data.fiveWeeks.FiveWeekChartPoint
import com.jaiswal.bitcointrends.repository.ChartRepository
import com.jaiswal.bitcointrends.repository.local.ChartLocalRepository
import com.jaiswal.bitcointrends.repository.remote.ChartRemoteRepository
import com.jjoe64.graphview.series.DataPoint
import java.text.DateFormat
import java.util.*


class GraphActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val dataBase: ChartDataBase = ChartDataBase.invoke(application)
    var lastRepositoryLocal = false
    private var chartRepository: ChartRepository
    var data: LiveData<List<FiveWeekChartPoint>>

    init {
        chartRepository = getRepository()
        data = chartRepository.getChartData()
    }

    fun getRemoteRepository(): ChartRepository {
        return if(isNetworkAvailable())
            ChartRemoteRepository(dataBase)
        else
            chartRepository
    }

    private fun getRepository(): ChartRepository {
        if (isNetworkAvailable()) {
            if (isStaleDataInCache()) {
                lastRepositoryLocal = false
                return ChartRemoteRepository(dataBase)
            }
        }
        lastRepositoryLocal = true
        return ChartLocalRepository(dataBase)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

    private fun isStaleDataInCache(): Boolean {
        val currentDate = Calendar.getInstance().time
        val sdf = DateFormat.getDateTimeInstance()

        val sharedPref: SharedPreferences = application.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val lastFetched = sharedPref.getString("lastFetched", "NO_DATA")

        if (lastFetched != "NO_DATA") {
            val lastFetchedDate = sdf.parse(lastFetched)
            return lastFetchedDate >= currentDate
        }
        return true
    }

    fun updatePrefs() {
        val currentDate = Calendar.getInstance().time
        val sdf = DateFormat.getDateTimeInstance()
        val currentDataString = sdf.format(currentDate)
        val sharedPref: SharedPreferences = application.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        sharedPref.edit().putString("lastFetched", currentDataString).apply()
    }

    fun convertCoordinatesToDataPoint(coordinates: List<FiveWeekChartPoint?>?): Array<DataPoint?>? {
        if (coordinates != null) {
            val dataPointArray = arrayOfNulls<DataPoint>(coordinates.size)
            for (x in 0 until coordinates.size) {

                if (coordinates[x]!!.getDataPoint() != null) {
                    dataPointArray[x] = coordinates[x]!!.getDataPoint()
                }

            }
            var sortedArray = dataPointArray.sortedWith(compareBy { it?.x })
            return sortedArray.toTypedArray()
        }
        return null
    }

    companion object {
        private const val PRIVATE_MODE = 0
        private const val PREF_NAME = "bitcoin_prefs"
    }
}