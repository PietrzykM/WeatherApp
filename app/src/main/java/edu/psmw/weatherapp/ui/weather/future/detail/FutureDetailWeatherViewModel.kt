package edu.psmw.weatherapp.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import edu.psmw.weatherapp.data.provider.UnitProvider
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.internal.lazyDefferd
import edu.psmw.weatherapp.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailWeatherViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDefferd {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}