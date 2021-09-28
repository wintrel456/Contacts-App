package com.gmail.l2t45s7e9.library.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.LatLngData
import com.gmail.l2t45s7e9.java.interactor.MapInteractor
import com.gmail.l2t45s7e9.library.interfaces.SchedulersProvider
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class MapViewModel
@Inject constructor(
    private val mapInteractor: MapInteractor,
    private val schedulersProvider: SchedulersProvider
) : ViewModel() {
    private var mutableLiveData = MutableLiveData<List<Contact>>()
    private var routeMutableLiveData = MutableLiveData<List<LatLng>>()
    private var disposable = CompositeDisposable()
    val contacts: LiveData<List<Contact>>
        get() = mutableLiveData
    val route: LiveData<List<LatLng>>
        get() = routeMutableLiveData

    fun getContactMarkers() {
        disposable.add(
            mapInteractor.contactMarkers
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe { markers: List<Contact> -> mutableLiveData.setValue(markers) }
        )
    }

    fun getContactMarker(id: String) {
        disposable.add(
            mapInteractor.getContactMarker(id)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
                .subscribe { markers: List<Contact> -> mutableLiveData.setValue(markers) }
        )
    }

    fun getRoute(firstMarker: LatLng, secondMarker: LatLng) {
        disposable.add(
            mapInteractor.getResponce(
                LatLngData(firstMarker.latitude, firstMarker.longitude),
                LatLngData(secondMarker.latitude, secondMarker.longitude)
            )
                .subscribeOn(schedulersProvider.io())
                .map(mapInteractor::getRoute)
                .subscribeOn(schedulersProvider.computation())
                .observeOn(schedulersProvider.ui())
                .subscribe { points: List<LatLngData> ->
                    routeMutableLiveData.value = points.map { LatLng(it.latitude, it.longitude) }
                }
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
