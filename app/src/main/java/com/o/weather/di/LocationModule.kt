package com.o.weather.di

import android.content.Context
import com.o.weather.providers.location.LocationProvider
import com.o.weather.providers.location.LocationProviderImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val LocationModule = Kodein.Module("Location_Module"){
    bind<LocationProvider>() with singleton { getLocationProvider(instance()) }
}

fun getLocationProvider(context: Context): LocationProvider{
    return LocationProviderImpl(context)
}