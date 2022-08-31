package com.example.runicash.apiManager

import com.example.runicash.apiManager.model.TopNews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_API_URL = "https://min-api.cryptocompare.com/data/"
const val API_KEY = "authorization: Apikey 4126e9d897d0de07d51d04a3efb7ed86ce197aa69088aeefe83d817b680bd777"

class ApiManager {

    private lateinit var apiService :ApiService

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    fun getTopNews( apiCallback: ApiCallback<ArrayList<Pair<String, String>>> ) {

        apiService.getTopNews().enqueue(object :Callback<TopNews> {
            override fun onResponse(call: Call<TopNews>, response: Response<TopNews>) {

                val body = response.body()!!

                val dataToSend :ArrayList<Pair<String, String>> = arrayListOf()

                body.data.forEach {
                    dataToSend.add(Pair(it.title, it.url))
                }

                apiCallback.onSuccess(dataToSend)

            }

            override fun onFailure(call: Call<TopNews>, t: Throwable) {

                apiCallback.onError(t.message!!)

            }


        } )

    }

    interface ApiCallback<T> {

        fun onSuccess(data: T)
        fun onError(errorMessage: String)

    }

}