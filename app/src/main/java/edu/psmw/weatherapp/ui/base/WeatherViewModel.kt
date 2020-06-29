package edu.psmw.weatherapp.ui.base

/**
 * ViewModel dla karty z obecną pogodą i lokalizajcją. łączy Model z Widokiem.
 */

import androidx.lifecycle.ViewModel
import edu.psmw.weatherapp.data.provider.UnitProvider
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.internal.UnitSystem
import edu.psmw.weatherapp.internal.lazyDefferd

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDefferd {
        forecastRepository.getWeatherLocation()
    }
}