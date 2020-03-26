package com.o.weather.providers.location

import android.location.Location
import androidx.lifecycle.LiveData

interface LocationProvider {

     fun getLocation(): LiveData<Location>

}