package com.gmail.l2t45s7e9.library.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Route {

    @SerializedName("overview_polyline")
    @Expose
    private var overviewPolyline: OverviewPolyline? = null


    fun getOverviewPolyline(): OverviewPolyline? {
        return overviewPolyline
    }

    fun setOverviewPolyline(overviewPolyline: OverviewPolyline?) {
        this.overviewPolyline = overviewPolyline
    }

}