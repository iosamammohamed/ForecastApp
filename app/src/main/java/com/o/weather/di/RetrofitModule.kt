package com.o.weather.di

import com.o.weather.data.remote.WeatherApi
import com.o.weather.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val RetrofitModule = Kodein.Module("Retrofit_Module"){
    bind<Interceptor>() with singleton { getInterceptor() }
    bind<HttpLoggingInterceptor>() with singleton { getLoggerInterceptor() }
    bind<OkHttpClient>() with singleton { getOkHttp(instance(), instance()) }
    bind<Retrofit>() with singleton { getRetrofit(instance()) }
    bind<WeatherApi>() with singleton { getWeatherApi(instance()) }
}


fun getLoggerInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
}

fun getInterceptor(): Interceptor{
     return Interceptor {chain ->
         val url = chain.request()
             .url
             .newBuilder()
             .addQueryParameter(Constants.ACCESS_KEY, Constants.API_KEY)
             .build()
         val request = chain.request()
             .newBuilder()
             .url(url).build()
            return@Interceptor chain.proceed(request)
     }
 }


fun getOkHttp(interceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient{
    return OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        //.addInterceptor(connectivityInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

fun getWeatherApi(reoorfit: Retrofit): WeatherApi{
     return reoorfit.create(WeatherApi::class.java)
}

