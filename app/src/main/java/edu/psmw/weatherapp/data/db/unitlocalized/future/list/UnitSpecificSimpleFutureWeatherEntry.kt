package edu.psmw.weatherapp.data.db.unitlocalized.future.list

/**
 * Interfejs UnitSpecificSimpleFutureWeatherEntry tworzy szablon dla implementacji systemu metrycznego
 * bądź jednostek imperialnych dla prognozy pogodoy najbliższych dni w formie listy.
 */

import org.threeten.bp.LocalDate

interface UnitSpecificSimpleFutureWeatherEntry {
    val date: LocalDate
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
}