package edu.psmw.weatherapp

import android.app.Application
import edu.psmw.weatherapp.data.db.ForecastDatabase
import edu.psmw.weatherapp.data.network.*
import edu.psmw.weatherapp.data.repository.ForecastRepository
import edu.psmw.weatherapp.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
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
    }
}