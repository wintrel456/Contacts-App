package com.gmail.l2t45s7e9.library.repository

import android.content.Context
import androidx.room.Room
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import com.gmail.l2t45s7e9.library.pojo.RouteFromGMaps
import com.gmail.l2t45s7e9.library.pojo.RouteResponce
import com.google.android.gms.maps.model.LatLng
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


class MapRepository(_context: Context) {
    private var context: Context = _context
    private var db: ContactAddressDataBase

    init {
        db = Room.databaseBuilder(
                context,
                ContactAddressDataBase::class.java,
                "contact-address2"
        ).build()
    }

    fun getMarkers(): Single<List<Contact>> {
        return Single.fromCallable { db.contactDao().all }
                .subscribeOn(Schedulers.io())
    }

    fun getMarker(id: String): Single<List<Contact>> {
        return db.contactDao().loadContactForMap(id)
                .onErrorReturnItem(arrayListOf())
                .subscribeOn(Schedulers.io())
    }

    fun route(firstMarker: LatLng, secondMarker: LatLng): Single<RouteFromGMaps> {
        val position = firstMarker.latitude.toString() + "," + firstMarker.longitude.toString()
        val destination = secondMarker.latitude.toString() + "," + secondMarker.longitude.toString()
        return RouteResponce().getInstance()
                .getApi()
                .getRoute(position, destination, "walking", context.getString(R.string.API_KEY), "ru")
                .subscribeOn(Schedulers.io())
    }

}
