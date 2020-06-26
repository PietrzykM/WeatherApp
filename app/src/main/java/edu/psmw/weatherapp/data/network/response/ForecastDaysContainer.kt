package edu.psmw.weatherapp.data.network.response


import com.google.gson.annotations.SerializedName
import edu.psmw.weatherapp.data.db.entity.FutureWeaterEntry

data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeaterEntry>
)