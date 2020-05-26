package edu.psmw.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModel
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.internal.UnitSystem
import edu.psmw.weatherapp.internal.lazyDefferd

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC//przygotowanie pod ustawienia systemowe przez użytkownika

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDefferd {
        forecastRepository.getCurrentWeather(isMetric)
    }
}
