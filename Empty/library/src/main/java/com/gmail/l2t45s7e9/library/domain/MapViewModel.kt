package com.gmail.l2t45s7e9.library.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.pojo.RouteFromGMaps
import com.gmail.l2t45s7e9.library.repository.MapRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MapViewModel(
        _context: Context
) : ViewModel() {
    private var mapRepository: MapRepository = MapRepository(_context)
    private var mutableLiveData = MutableLiveData<List<Contact>>()
    private var disposable = CompositeDisposable()
    private var list = MutableLiveData<List<LatLng>>()

    fun getContactMarkers(): LiveData<List<Contact>> {
        disposable.add(
                mapRepository.getMarkers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { markers: List<Contact>? -> mutableLiveData.setValue(markers) }
        )
        return mutableLiveData
    }

    fun getContactMarker(id: String): LiveData<List<Contact>> {
        disposable.add(
                mapRepository.getMarker(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { markers: List<Contact>? -> mutableLiveData.setValue(markers) }
        )
        return mutableLiveData
    }

    fun getRoute(firstMarker: LatLng, secondMarker: LatLng): LiveData<List<LatLng>> {
        disposable.add(
                mapRepository.route(firstMarker, secondMarker)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { markers: RouteFromGMaps ->
                            try {
                                list.setValue(PolyUtil.decode(markers.getRoutes()[0].getOverviewPolyline()?.getPoints()))
                            } catch (t: Throwable) {
                                list.setValue(mutableListOf())
                            }
                        }
        )
        return list
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}