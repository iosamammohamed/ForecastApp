package com.o.weather.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.o.weather.data.model.WeatherResponse
import com.o.weather.data.repository.weather.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(val weatherRepository: WeatherRepository) : ViewModel() {

    var weatherLiveData: MutableLiveData<WeatherResponse.Weather> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>(false)

    private val compositeDisposable = CompositeDisposable()

    fun getCurrentWeather(city: String, unit: String, shouldRefresh: Boolean){

        isLoading.value = true
        compositeDisposable.add(
            weatherRepository.getCurrentWeather(city, unit, shouldRefresh)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {weatherData ->
                weatherLiveData.postValue(weatherData)
                isLoading.value = false
            },{error ->
                println(error)
                isLoading.value = false
            })
        )


    }

}
