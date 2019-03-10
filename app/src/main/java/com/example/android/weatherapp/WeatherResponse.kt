package com.example.android.weatherapp

/**Data classes for response from site.
* Still need to test what information I need and if I can do it like this*/
object Model {
    data class WeatherResponse(val cod: Int, val message: Double, val cnt: Int)

    data class WeatherForecast(val list: Array<WeatherData>)

    data class WeatherData(
        val dt: Int,
        val main: WeatherMain,
        val weather: WeatherWeather,
        val clouds: Int,
        val wind: WeatherWind
    )

    data class WeatherMain(
        val temp: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Double,
        val humidity: Int,
        val temp_kf: Double
    )

    data class WeatherWeather(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    data class WeatherWind(val speed: Double, val deg: Double)
}
