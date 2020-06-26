package edu.psmw.weatherapp.data.repository

import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.db.entity.WeatherLocation
import edu.psmw.weatherapp.data.db.unitlocalized.current.UnitSpecificCurrentWeaterEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeaterEntry>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}