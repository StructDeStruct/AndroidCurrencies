package com.example.structdestruct.currencies.retrofit

import com.example.structdestruct.currencies.model.Rate

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CryptoCompareServiceApi {
    //https://min-api.cryptocompare.com/data/v2/histoday ?fsym=BTC&tsym=USD&limit=4

    @GET("data/v2/histoday")
    fun GetHistoryDay(@Query("fsym") fsym: String,
                      @Query("tsym") tsym: String,
                      @Query("limit") limit: Int) : Call<Rate>
}