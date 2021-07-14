package com.gmail.l2t45s7e9.library.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class OverviewPolyline {
    @SerializedName("points")
    @Expose
    private lateinit var points: String

    fun getPoints(): String {
        return points
    }

    fun setPoints(points: String) {
        this.points = points
    }
}