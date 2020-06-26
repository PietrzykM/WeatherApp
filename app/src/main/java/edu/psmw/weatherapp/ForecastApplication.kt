package edu.psmw.weatherapp

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import edu.psmw.weatherapp.data.db.ForecastDatabase
import edu.psmw.weatherapp.data.network.*
import edu.psmw.weatherapp.data.provider.LocationProvider
import edu.psmw.weatherapp.data.provider.LocationProviderImpl
import edu.psmw.weatherapp.data.provider.UnitProvider
import edu.psmw.weatherapp.data.provider.UnitProviderImpl
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.data.repository.ForecastRepositoryImpl
import edu.psmw.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import edu.psmw.weatherapp.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import edu.psmw.weatherapp.ui.weather.future.list.FutureListWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton {ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton  { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton {ApixuWeatherApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton  { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton  { ForecastRepositoryImpl(instance(),instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory() { detailDate: LocalDate -> FutureDetailWeatherViewModelFactory(detailDate, instance(), instance())}
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}