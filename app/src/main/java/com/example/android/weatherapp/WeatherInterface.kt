package com.example.android.weatherapp

/*Interface for getting forecast from site
* Need to test it and implement it*/

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    @GET("/data/2.5/forecast")
    fun getWeather(@Query("lat") lat: Float, @Query("lon") lon: Float, @Query("APPID") appId: String)
    companion object {
        val instance: WeatherInterface by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(WeatherInterface::class.java)
        }
    }
}