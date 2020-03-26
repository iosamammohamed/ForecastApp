package com.o.weather.ui.weather.current

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.o.weather.data.model.WeatherResponse.Weather
import com.o.weather.data.repository.weather.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {

    private var weatherMLiveData: MutableLiveData<Weather> = MutableLiveData()
    val weatherLiveData: LiveData<Weather>
    get() = weatherMLiveData
    var isLoading = MutableLiveData<Boolean>(false)
    var isEmpty = MutableLiveData<Boolean>(false)

    private val compositeDisposable = CompositeDisposable()

    fun getCurrentWeather(city: String, shouldRefresh: Boolean){
        isLoading.value = true
        compositeDisposable.add(
            weatherRepository.getCurrentWeather(city, shouldRefresh)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {weatherData ->
                weatherMLiveData.value = weatherData
                isLoading.value = false
                isEmpty.value = false
            },{error ->
                isLoading.value = false
                isEmpty.value = true
            })
        )


    }

}
