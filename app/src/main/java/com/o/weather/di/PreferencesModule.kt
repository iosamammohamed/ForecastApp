package com.o.weather.di

import android.content.Context
import com.o.weather.providers.location.LocationProvider
import com.o.weather.providers.location.LocationProviderImpl
import com.o.weather.providers.preference.PreferencesProvider
import com.o.weather.providers.preference.PreferencesProviderImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


val PreferencesModule = Kodein.Module("Preferences_Module"){
    bind<PreferencesProvider>() with singleton { getPreferencesProvider(instance()) }
}

fun getPreferencesProvider(context: Context): PreferencesProvider {
    return PreferencesProviderImpl(context)
}