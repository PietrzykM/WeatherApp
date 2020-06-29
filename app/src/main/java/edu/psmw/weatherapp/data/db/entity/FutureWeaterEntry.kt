package edu.psmw.weatherapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import edu.psmw.weatherapp.data.db.entity.Day

/**
 * Klasa definiująca dane zawarte na liście przewidywanej pogody .
 *
 */

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class FutureWeaterEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: String,
    @Embedded
    val day: Day
)