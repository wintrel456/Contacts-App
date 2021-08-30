package com.gmail.l2t45s7e9.library.repository

import android.content.Context
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.java.interactor.LatLngData
import com.gmail.l2t45s7e9.java.interactor.MapRepository
import com.gmail.l2t45s7e9.java.interactor.RouteFromGMapsData
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.dataBase.ContactAddressDataBase
import com.gmail.l2t45s7e9.library.dataBase.ContactData
import com.gmail.l2t45s7e9.library.pojo.RouteApi
import com.google.maps.android.PolyUtil
import io.reactivex.rxjava3.core.Single


class MapRepositoryImpl(
    private val db: ContactAddressDataBase,
    private val context: Context,
    private val routeApi: RouteApi
) : MapRepository {

    override fun getMarkers(): Single<List<Contact>> {
        return Single.fromCallable { db.contactDao().all }
            .map { result ->
                return@map mapMarkers(result)
            }
    }

    override fun getMarker(id: String): Single<List<Contact>> {
        return db.contactDao().loadContactForMap(id)
            .onErrorReturnItem(arrayListOf())
            .map { result ->
                return@map mapMarkers(result)
            }
    }

    private fun mapMarkers(result: List<ContactData>): List<Contact> {
        val markers = mutableListOf<Contact>()
        result.forEach {
            markers.add(
                Contact(
                    it.id,
                    it.contactAddress,
                    it.latitude,
                    it.longitude
                )
            )
        }
        return markers
    }

    override fun getResponce(
        firstMarker: LatLngData,
        secondMarker: LatLngData
    ): Single<RouteFromGMapsData> {
        val position = firstMarker.latitude.toString() + "," + firstMarker.longitude.toString()
        val destination = secondMarker.latitude.toString() + "," + secondMarker.longitude.toString()
        return routeApi
            .getRoute(position, destination, "walking", context.getString(R.string.API_KEY), "ru")
            .map { listRoute ->
                val list = mutableListOf<RouteFromGMapsData.Route>()
                listRoute.routes.forEach {
                    list.add(
                        RouteFromGMapsData.Route(
                            RouteFromGMapsData.OverviewPolyline(
                                it.overviewPolyline?.points.orEmpty()
                            )
                        )
                    )
                }
                RouteFromGMapsData(list)
            }

    }

    override fun getRoute(routeFromGMapsData: RouteFromGMapsData): List<LatLngData> {
        val list = PolyUtil.decode(routeFromGMapsData.routes[0].overviewPolyline.points)
        return list.map { LatLngData(it.latitude, it.longitude) }
    }
}
