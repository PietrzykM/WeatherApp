package edu.psmw.weatherapp.data.db.unitlocalized.future.detail

import org.threeten.bp.LocalDate

/**
 * Interfejs UnitSpecificDetailFutureWeatherEntry tworzy szablon dla implementacji systemu metrycznego
 * bądź jednostek imperialnych dla dni w najbliższym tygodniu (karta ze szczegółowymi informacjami
 * na temat konkretnego dnia.
 */

interface UnitSpecificDetailFutureWeatherEntry {
    val date: LocalDate
    val maxTemperature: Double
    val minTemperature: Double
    val avgTemperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val maxWindSpeed: Double
    val totalPrecipitation: Double
    val avgVisibilityDistance: Double
    val uv: Double
}