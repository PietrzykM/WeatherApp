package edu.psmw.weatherapp.data.repository

import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.db.CurrentWeatherDao
import edu.psmw.weatherapp.data.db.WeatherLocationDao
import edu.psmw.weatherapp.data.db.entity.WeatherLocation
import edu.psmw.weatherapp.data.db.unitlocalized.current.UnitSpecificCurrentWeaterEntry
import edu.psmw.weatherapp.data.network.WeatherNetworkDataSource
import edu.psmw.weatherapp.data.network.response.CurrentWeatherResponse
import edu.psmw.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currenWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
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

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(fetchWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currenWeatherDao.upsert(fetchWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            return
        }

        if (isFetchCurrenNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrenNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo  )
    }

}