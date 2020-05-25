package edu.psmw.weatherapp.data.repository

import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.db.CurrentWeatherDao
import edu.psmw.weatherapp.data.db.unitlocalized.UnitSpecificCurrentWeaterEntry
import edu.psmw.weatherapp.data.network.WeatherNetworkDataSource
import edu.psmw.weatherapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currenWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeaterEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currenWeatherDao.getWeatherMetric()
            else currenWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(fetchWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currenWeatherDao.upsert(fetchWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if (isFetchCurrenNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angeles",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrenNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo  )
    }

}