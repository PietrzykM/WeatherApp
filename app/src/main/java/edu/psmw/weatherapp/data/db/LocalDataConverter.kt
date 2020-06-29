package edu.psmw.weatherapp.data.db

/**
 * Obiekt zajmujący się konwersją daty do odpowiedniego formatu (jako, że czasem jest przekazywana
 * w formie stringa wywołujemy ten obiek, aby zapewnić odpowiedni format daty).
 */


import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object LocalDataConverter {
    @TypeConverter
    @JvmStatic
    fun stringToDate(str: String?) = str?.let {
        LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    @JvmStatic
    fun dateToString(dateTime: LocalDate?) = dateTime?.format (
        DateTimeFormatter.ISO_LOCAL_DATE
    )
}