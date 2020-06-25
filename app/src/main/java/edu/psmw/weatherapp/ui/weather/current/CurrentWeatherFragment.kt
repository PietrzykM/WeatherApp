package edu.psmw.weatherapp.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import edu.psmw.weatherapp.R
import edu.psmw.weatherapp.data.network.ApixuWeatherApiService
import edu.psmw.weatherapp.data.network.ConnectivityInterceptorImpl
import edu.psmw.weatherapp.data.network.WeatherNetworkDataSourceImpl
import edu.psmw.weatherapp.internal.glide.GlideApp
import edu.psmw.weatherapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory:CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch{
        val curretWeather = viewModel.weather.await()
        curretWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Cracow")
            updateDayToToday()
            updateTemperatures(it.temperature, it.feelsLikeTemperature)
            updateCondition(it.conditionText)
            updatePreciptation(it.precipitationVolume)
            updateWind(it.windDirection,it.windSpeed)
            updateVisibility(it.visibilityDistance)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String  {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDayToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLike: Double) {
        val unitAbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unitAbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePreciptation(precipitationVolume: Double){
        val unitAbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text ="Precipitation: $precipitationVolume $unitAbreviation"
    }
    private fun updateWind(windDirection: String, windSpeed: Double){
        val unitAbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text ="Wind: $windDirection, $windSpeed $unitAbreviation"
    }
    private fun updateVisibility(visibilityDistance: Double){
        val unitAbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        textView_precipitation.text ="Visibility: $visibilityDistance $unitAbreviation"
    }

}
