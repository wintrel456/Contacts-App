package com.gmail.l2t45s7e9.library.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RouteFromGMaps {
    @SerializedName("routes")
    @Expose
    private lateinit var routes: List<Route>

    fun getRoutes(): List<Route> {
        return routes
    }

    class Route {

        @SerializedName("overview_polyline")
        @Expose
        private var overviewPolyline: OverviewPolyline? = null


        fun getOverviewPolyline(): OverviewPolyline? {
            return overviewPolyline
        }

    }

    class OverviewPolyline {
        @SerializedName("points")
        @Expose
        private lateinit var points: String

        fun getPoints(): String {
            return points
        }
    }
}