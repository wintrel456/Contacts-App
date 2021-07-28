package com.gmail.l2t45s7e9.library.presentation.screens

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.domain.MapViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelMapFactory
import com.gmail.l2t45s7e9.library.interfaces.HasAppContainer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject


class MapFragment : Fragment(R.layout.map_fragment), OnMapReadyCallback {

    @Inject
    lateinit var mapFactory: ViewModelMapFactory
    private var gmap: GoogleMap? = null
    private var id: String? = null
    private val mapViewModel: MapViewModel by lazy {
        ViewModelProvider(this, mapFactory).get(MapViewModel::class.java)
    }
    private var markers = mutableListOf<Contact>()
    private var markerState = false
    private var firstMarker: LatLng? = null
    private var secondMarker: LatLng? = null

    private var onMarkerClick = OnMarkerClickListener { marker ->
        if (!markerState) {
            firstMarker = marker.position
            markerState = true
            Toast.makeText(context, R.string.first_marker_added, Toast.LENGTH_SHORT).show()
        } else {
            secondMarker = marker.position
            markerState = false
            Toast.makeText(context, R.string.second_marker_added, Toast.LENGTH_SHORT).show()
            firstMarker?.let { first ->
                secondMarker?.let { second ->
                    mapViewModel.getRoute(first, second)
                }
            }
        }
        true
    }

    override fun onAttach(context: Context) {
        val app = context.applicationContext as Application
        check(app is HasAppContainer)
        (app as HasAppContainer).apply {
            appContainer().plusMapContainer().inject(this@MapFragment)
        }
        super.onAttach(context)
        id = arguments?.getString("id")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager.beginTransaction().apply {
            val map = SupportMapFragment()
            map.getMapAsync(this@MapFragment)
            add(R.id.mapView, map)
            commit()
        }
        mapViewModel.contacts.observe(viewLifecycleOwner, {
            markers.addAll(it)
        })
        mapViewModel.route.observe(viewLifecycleOwner, {
            gmap?.let { it1 -> route(it, it1) }
            firstMarker = null
            secondMarker = null
        })
        if (id != null) {
            mapViewModel.getContactMarker(id.toString())
        } else {
            id = ""
            mapViewModel.getContactMarkers()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        loadMarkers()
    }

    private fun loadMarkers() {
        gmap?.apply {
            moveCamera(CameraUpdateFactory.newLatLng(LatLng(0.0, 0.0)))
            uiSettings.setAllGesturesEnabled(true)
            markers.forEach {
                addMarker(MarkerOptions()
                        .position(LatLng(it.latitude, it.longitude)))
            }
            setOnMarkerClickListener(onMarkerClick)
        }
    }

    private fun route(mPoints: List<LatLng>, googleMap: GoogleMap) {
        if (mPoints.isEmpty()) {
            Snackbar.make(requireView(), R.string.route_not_created, Snackbar.LENGTH_SHORT).show()
        } else {
            val width = TypedValue()
            resources.getValue(R.dimen.width, width, true)
            val line = PolylineOptions()
            line.width(width.float).color(R.color.Font)
            val latLngBuilder = LatLngBounds.Builder()
            mPoints.forEach {
                line.add(it)
                latLngBuilder.include(it)
            }
            googleMap.addPolyline(line)
            Snackbar.make(requireView(), R.string.route_created, Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        gmap = null
        super.onDestroyView()
    }
}
