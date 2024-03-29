package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface MapRepository {
    Single<List<Contact>> getMarkers();

    Single<List<Contact>> getMarker(String id);

    List<LatLngData> getRoute(RouteFromGMapsData routeFromGMapsData);

    Single<RouteFromGMapsData> getResponce(LatLngData firstMarker, LatLngData secondMarker);
}
