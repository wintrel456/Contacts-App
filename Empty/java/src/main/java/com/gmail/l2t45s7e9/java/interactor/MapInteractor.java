package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
public interface MapInteractor {
    Single<List<Contact>> getContactMarkers();

    Single<List<Contact>> getContactMarker(String id);

    List<LatLngData> getRoute(RouteFromGMapsData routeFromGMapsData);

    Single<RouteFromGMapsData> getResponce(LatLngData firstMarker, LatLngData secondMarker);
}
