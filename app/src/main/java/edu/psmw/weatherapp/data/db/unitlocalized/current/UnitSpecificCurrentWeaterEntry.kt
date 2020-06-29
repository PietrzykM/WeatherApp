package edu.psmw.weatherapp.data.db.unitlocalized.current

/**
 * Interfejs UnitSpecificCurrentWeaterEntry tworzy szablon dla implementacji systemu metrycznego
 * bądź jednostek imperialnych. Stworzono dla większegj przejrzystości kodu i uniknięcia powilania
 * tych samych fragmentów.
 */

interface UnitSpecificCurrentWeaterEntry {
    val temperature:Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}