package com.android.asteroidradar.api

import com.google.gson.JsonObject
import com.android.asteroidradar.models.PictureOfDay
import com.android.asteroidradar.utils.ApiKey
import com.android.asteroidradar.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class NetworkRequest {

    val service : Service

    init {
        val retrofit : Retrofit = Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(Service::class.java)
    }


    interface Service {
        @GET("neo/rest/v1/feed")
        suspend fun getAsteroids(
            @Query("start_date") startDate : String,
            @Query("end_date") endDate : String,
            @Query("api_key") apiKey : String = Constants.API_KEY
        ): JsonObject

        @GET("planetary/apod")
        suspend fun getPictureOfTheDay(
            @Query("api_key") apiKey: String = Constants.API_KEY
        ): PictureOfDay
    }
}


