package edu.psmw.weatherapp.data.repository

import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.db.entity.WeatherLocation
import edu.psmw.weatherapp.data.db.unitlocalized.current.UnitSpecificCurrentWeaterEntry
import edu.psmw.weatherapp.data.db.unitlocalized.future.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeaterEntry>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean):
            LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getWeatherLocation(): LiveData<WeatherLocation>
}