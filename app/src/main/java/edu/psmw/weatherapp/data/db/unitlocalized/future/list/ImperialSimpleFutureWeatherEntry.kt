package edu.psmw.weatherapp.data.db.unitlocalized.future.list

/**
 * Implementacja interfejsu UnitSpecificSimpleFutureWeatherEntry dla jednostek imperialnych
 * (selekcjonowane są dane dla jednostek imperialnych).
 */

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate

class ImperialSimpleFutureWeatherEntry (
    @ColumnInfo(name = "date")
    override val date: LocalDate,
    @ColumnInfo(name = "avgtempF")
    override val avgTemperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String
) : UnitSpecificSimpleFutureWeatherEntry