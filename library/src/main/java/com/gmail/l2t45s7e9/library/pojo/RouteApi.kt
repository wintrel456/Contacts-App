package com.gmail.l2t45s7e9.library.pojo

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteApi {
    @GET("/maps/api/directions/json?")
    fun getRoute(
        @Query(value = "origin") position: String,
        @Query(value = "destination") destination: String,
        @Query("mode") mode: String,
        @Query("key") key: String,
        @Query("language") language: String
    ): Single<RouteFromGMaps>
}
