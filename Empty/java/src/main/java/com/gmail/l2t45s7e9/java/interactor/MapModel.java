package com.gmail.l2t45s7e9.java.interactor;

import com.gmail.l2t45s7e9.java.entity.Contact;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
public class MapModel implements MapInteractor {
    private MapRepository mapRepository;

    public MapModel(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    @Override
    public Single<List<Contact>> getContactMarkers() {
        return mapRepository.getMarkers();
    }

    @Override
    public Single<List<Contact>> getContactMarker(String id) {
        return mapRepository.getMarker(id);
    }

    @Override
    public List<LatLngData> getRoute(RouteFromGMapsData routeFromGMapsData) {
        return mapRepository.getRoute(routeFromGMapsData);
    }

    @Override
    public Single<RouteFromGMapsData> getResponce(LatLngData firstMarker, LatLngData secondMarker) {
        return mapRepository.getResponce(firstMarker, secondMarker);
    }
}
