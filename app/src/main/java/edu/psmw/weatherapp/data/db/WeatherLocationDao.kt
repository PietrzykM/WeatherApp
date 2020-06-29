package edu.psmw.weatherapp.data.db

/**
 * Data Access Object- interfejs dający sablon dla komunikacji pomiędzy danymmi a aplikacją.
 * WeatherLocationDao obejmuje dane lokalizacyjne (jako, że aplikacja przetrzymuje dane na temat lokalizacji
 * i poprzedniego odczytu danych pogodowych z API na wypadek, gdbyby w chwili uruchomienia aplikacji
 * użytkownik nie miał dostępu do internetu i możliwości pobrania danych aktualnych.
 */

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.psmw.weatherapp.data.db.entity.WEATHER_LOCATION_ID
import edu.psmw.weatherapp.data.db.entity.WeatherLocation

@Dao
interface WeatherLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherLocation: WeatherLocation)

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocation(): LiveData<WeatherLocation>

    @Query("select * from weather_location where id = $WEATHER_LOCATION_ID")
    fun getLocationNonLive(): WeatherLocation?
}