package com.gmail.l2t45s7e9.library.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RouteFromGMaps {
    @SerializedName("routes")
    @Expose
    private lateinit var routes: List<Route>

    @SerializedName("status")
    @Expose
    private var status: String? = null

    fun getRoutes(): List<Route> {
        return routes
    }

    fun setRoutes(routes: List<Route>) {
        this.routes = routes
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String?) {
        this.status = status
    }
}