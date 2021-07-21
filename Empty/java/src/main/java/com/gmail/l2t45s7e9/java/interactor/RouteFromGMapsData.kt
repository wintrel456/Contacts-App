package com.gmail.l2t45s7e9.java.interactor

class RouteFromGMapsData(val routes: List<Route>) {
    class Route(val overviewPolyline: OverviewPolyline)
    class OverviewPolyline(val points: String)
}