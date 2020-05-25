package edu.psmw.weatherapp.data.repository

import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.db.unitlocalized.UnitSpecificCurrentWeaterEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeaterEntry>
}