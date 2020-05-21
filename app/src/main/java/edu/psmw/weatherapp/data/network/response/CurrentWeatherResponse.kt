package edu.psmw.weatherapp.data.network.response


import com.google.gson.annotations.SerializedName
import edu.psmw.weatherapp.data.db.entity.CurrentWeatherEntry
import edu.psmw.weatherapp.data.db.entity.Location

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location
)