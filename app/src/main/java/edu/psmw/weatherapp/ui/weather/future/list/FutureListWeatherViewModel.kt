package edu.psmw.weatherapp.ui.weather.future.list

import androidx.lifecycle.ViewModel

import edu.psmw.weatherapp.data.provider.UnitProvider
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.internal.lazyDefferd
import edu.psmw.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDefferd {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}