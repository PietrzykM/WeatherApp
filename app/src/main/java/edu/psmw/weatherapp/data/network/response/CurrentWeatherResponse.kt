package edu.psmw.weatherapp.data.network.response




import com.google.gson.annotations.SerializedName
import edu.psmw.weatherapp.data.db.entity.CurrentWeatherEntry
import edu.psmw.weatherapp.data.db.entity.WeatherLocation

data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)