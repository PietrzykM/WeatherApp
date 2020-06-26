package edu.psmw.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import edu.psmw.weatherapp.data.provider.UnitProvider
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.internal.UnitSystem
import edu.psmw.weatherapp.internal.lazyDefferd

/**
 *  ViewModel of CurrentWeather panel
 * overrides functions: onCreatePreferences onActivityCreated
 */

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDefferd {
        forecastRepository.getCurrentWeather(isMetric)
    }
    val weatherLocation by lazyDefferd{
        forecastRepository.getWeatherLocation()
    }
}
