package com.o.weather.providers.location

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*

class LocationProviderImpl(val context: Context) : LocationProvider {

    private var locationMLiveData = MutableLiveData<Location>()
    val locationLiveData: LiveData<Location>
    get() = locationMLiveData

    private var fusedLocationProviderClient: FusedLocationProviderClient
    private var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var mCurrentLocation: Location? = null

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = (10 * 1000).toLong() // 10 seconds
        locationRequest.fastestInterval = (5 * 1000).toLong() // 5 seconds

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        mCurrentLocation = location
                        locationMLiveData.value = mCurrentLocation
                        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                    }
                }
            }
        }


    }

    override  fun getLocation(): LiveData<Location>{
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                mCurrentLocation = location
                locationMLiveData.value = mCurrentLocation
            } else {
                 fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }
        }
        return  locationLiveData
    }


}