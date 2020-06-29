package edu.psmw.weatherapp.data.repository

/**
 * Klasa pobierająca dane o obecnej pogodznie, lokalizacji, prognozie na następne dni z innych klas
 * i obiektów.
 */
import androidx.lifecycle.LiveData
import edu.psmw.weatherapp.data.db.CurrentWeatherDao
import edu.psmw.weatherapp.data.db.FutureWeatherDao
import edu.psmw.weatherapp.data.db.WeatherLocationDao
import edu.psmw.weatherapp.data.db.entity.WeatherLocation
import edu.psmw.weatherapp.data.db.unitlocalized.current.UnitSpecificCurrentWeaterEntry
import edu.psmw.weatherapp.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import edu.psmw.weatherapp.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import edu.psmw.weatherapp.data.network.FORECAST_DAYS_COUNT
import edu.psmw.weatherapp.data.network.WeatherNetworkDataSource
import edu.psmw.weatherapp.data.network.response.CurrentWeatherResponse
import edu.psmw.weatherapp.data.network.response.FutureWeatherResponse
import edu.psmw.weatherapp.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currenWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newCFutureWeather ->
                persistFetchedFutureWeather(newCFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeaterEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if(metric) currenWeatherDao.getWeatherMetric()
            else currenWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getFutureWeatherList(
        startDate: LocalDate,
        metric: Boolean
    ): LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>> {
        return  withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if(metric) futureWeatherDao.getSimpleWeatherForecastsMetric(startDate)
            else futureWeatherDao.getSimpleWeatherForecastsImperial(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(
        date: LocalDate,
        metric: Boolean
    ): LiveData<out UnitSpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (metric) futureWeatherDao.getDetailedWeatherByDateMetric(date)
            else futureWeatherDao.getDetailedWeatherByDateImperial(date)
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

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        fun deleteOldForecastData() {
            val today = LocalDate.now()
            futureWeatherDao.deleteOldEntries(today)
        }
        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.futureWeatherEntries.entries
            futureWeatherDao.insert(futureWeatherList)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = weatherLocationDao.getLocationNonLive()

        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchCurrentWeather()
            fetchFutureWeather()
            return
        }

        if (isFetchCurrenNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

        if (isFetchFutureNeeded())
            fetchFutureWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchFutureWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private suspend fun fetchFutureWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrenNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo  )
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = LocalDate.now()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today)
        return futureWeatherCount < FORECAST_DAYS_COUNT
    }

}