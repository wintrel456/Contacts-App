package com.gmail.l2t45s7e9.library.pojo

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RouteResponce {
    private val BASE_URL = "https://maps.googleapis.com"
    private var mRetrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)

        mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
    }

    fun getInstance(): RouteResponce {
        return RouteResponce()
    }

    fun getApi(): RouteApi {
        return mRetrofit.create(RouteApi::class.java)
    }
}