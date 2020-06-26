package edu.psmw.weatherapp.data.network

import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.network.response.CurrentWeatherResponse
import edu.psmw.weatherapp.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}