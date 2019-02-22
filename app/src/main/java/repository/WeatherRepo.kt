package repository

import com.example.android.weatherapp.WeatherForecast
import com.example.android.weatherapp.WeatherInterface
import com.example.android.weatherapp.WeatherResponse
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepo(var weatherInterface: WeatherInterface) {
    fun searchLatLng(position: LatLng, appID: String, callBack: (List<WeatherForecast>?) -> Unit) {
        val weatherCall = weatherInterface.getWeather(position.latitude, position.longitude, appID)
        weatherCall.enqueue(object : Callback<WeatherResponse> {
            override fun onFailure(call: Call<WeatherResponse>?, t: Throwable?) {
                callBack(null)
            }

            override fun onResponse(call: Call<WeatherResponse>?, response: Response<WeatherResponse>?) {
                val body = response?.body()
                callBack(body?.results)
            }
        })
    }
}