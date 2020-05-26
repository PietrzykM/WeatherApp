package edu.psmw.weatherapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import edu.psmw.weatherapp.data.db.ForecastDatabase
import edu.psmw.weatherapp.data.network.*
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.data.repository.ForecastRepositoryImpl
import edu.psmw.weatherapp.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton {ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton  { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton {ApixuWeatherApiService(instance())}
        bind<WeatherNetworkDataSource>() with singleton  { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton  { ForecastRepositoryImpl(instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}