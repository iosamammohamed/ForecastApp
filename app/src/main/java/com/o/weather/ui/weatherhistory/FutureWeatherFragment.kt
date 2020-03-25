package com.o.weather.ui.weatherhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import com.o.weather.R

class FutureWeatherFragment : Fragment(R.layout.weather_future_fragment) {

    private lateinit var viewModelFuture: FutureWeatherViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelFuture = ViewModelProvider(this).get(FutureWeatherViewModel::class.java)
    }

}
