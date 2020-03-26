package com.o.weather

import android.app.Application
import com.o.weather.di.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class WeatherApplication: Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@WeatherApplication))
        importAll(RetrofitModule, DatabaseModule, RepositoryModule, ViewModelFactoryModule, LocationModule, PreferencesModule)
    }

}