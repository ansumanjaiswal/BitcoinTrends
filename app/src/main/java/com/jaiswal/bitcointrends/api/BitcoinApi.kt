package com.jaiswal.bitcointrends.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BitcoinApi {
    companion object {

        private var retrofit: Retrofit? = null

        val client: Retrofit
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(" https://api.blockchain.info/")
                        .build()
                }
                return retrofit!!
            }
    }
}