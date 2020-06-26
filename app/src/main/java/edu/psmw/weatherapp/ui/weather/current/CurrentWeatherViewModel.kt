package edu.psmw.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import edu.psmw.weatherapp.data.provider.UnitProvider
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.internal.UnitSystem
import edu.psmw.weatherapp.internal.lazyDefferd
import edu.psmw.weatherapp.ui.base.WeatherViewModel

/**
 *  ViewModel of CurrentWeather panel
 * overrides functions: onCreatePreferences onActivityCreated
 */

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {


    val weather by lazyDefferd {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}
