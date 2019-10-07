package com.jaiswal.bitcointrends.api

import com.jaiswal.bitcointrends.models.ChartData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChartsService {

    @GET("charts/transactions-per-second?")
    fun getChart(@Query("timespan")timespan: String,
                   @Query("rollingAverage")rollingAverage: String,
                   @Query("format")average: String): Call<ChartData>
}