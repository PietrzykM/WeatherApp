package edu.psmw.weatherapp.data.db.unitlocalized.current

/**
 * Aplikacja pozwala użytkownikowi wybrac pomiędzy systemem merycznym a jednostkami imperialnymi.
 *  MetricCurrentWeatherEntry tworzy specyfikację dla systemu metrycznego.
 * Jest to implementacja interfejsu UnitSpecificCurrentWeaterEntry.
 */

import androidx.room.ColumnInfo

data class MetricCurrentWeatherEntry (
    @ColumnInfo(name = "tempC")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windKph")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visKm")
    override val visibilityDistance: Double

) : UnitSpecificCurrentWeaterEntry