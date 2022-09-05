package com.example.runicash.apiManager

import com.example.runicash.apiManager.model.ChartData
import com.example.runicash.apiManager.model.TopCoins
import com.example.runicash.apiManager.model.TopNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers(API_KEY)
    @GET("v2/news/")
    fun getTopNews(
        @Query("sortOrder") sortOrder: String = "popular"
    ): Call<TopNews>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") to_symbol: String = "USD",
        @Query("limit") limit_data: Int = 15
    ): Call<TopCoins>

    @Headers(API_KEY)
    @GET("{period}")
    fun getChartData(
        @Path("period") period :String,
        @Query("fsym") fromSymbol :String,
        @Query("limit") limit :Int,
        @Query("aggregate")  aggregate:Int,
        @Query("tsym") toSymbol :String = "USD"
    ) :Call<ChartData>

}
