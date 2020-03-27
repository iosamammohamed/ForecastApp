package com.o.weather.providers.preference

interface PreferencesProvider {

    fun getCustomLocation(): String
    fun isUsingDeviceLocation(): Boolean

}