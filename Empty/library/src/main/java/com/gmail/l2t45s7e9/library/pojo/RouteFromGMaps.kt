package com.gmail.l2t45s7e9.library.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class RouteFromGMaps(
        @SerializedName("routes")
        @Expose
        val routes: List<Route>
) {
    class Route(
            @SerializedName("overview_polyline")
            @Expose
            val overviewPolyline: OverviewPolyline? = null)

    class OverviewPolyline(
            @SerializedName("points")
            @Expose
            val points: String
    )
}