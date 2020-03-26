package com.o.weather.providers.preference

interface PreferencesProvider {

    fun getManualLocation(): String
    fun isUsingDeviceLocation(): Boolean

}