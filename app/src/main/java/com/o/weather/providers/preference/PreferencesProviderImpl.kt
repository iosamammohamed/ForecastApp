package com.o.weather.providers.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.o.weather.R

class PreferencesProviderImpl(val context: Context): PreferencesProvider {
    private val appContext = context.applicationContext
    private val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getCustomLocation(): String{
        return preferences.getString(appContext.getString(R.string.KEY_CUSTOM_LOCATION),
                                    appContext.getString(R.string.CUSTOM_LOCATION_LOCATION_DEFAULT_VALUE))!!
    }

    override fun isUsingDeviceLocation(): Boolean{
        return preferences.getBoolean(appContext.getString(R.string.KEY_USE_DEVICE_LOCATION), true)
    }

}