package com.gmail.l2t45s7e9.library.presentation.screens

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.l2t45s7e9.java.entity.Contact
import com.gmail.l2t45s7e9.library.R
import com.gmail.l2t45s7e9.library.domain.MapViewModel
import com.gmail.l2t45s7e9.library.domain.factories.ViewModelMapFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar


class MapFragment : Fragment(R.layout.map_fragment), OnMapReadyCallback {

    private lateinit var gmap: GoogleMap
    private lateinit var mapTag: TextView
    private lateinit var mapTag2: TextView
    private lateinit var mapView: MapView
    private lateinit var id: String
    private lateinit var mapViewModel: MapViewModel
    private var markers = mutableListOf<Contact>()
    private var state = false
    private var firstMarker: LatLng? = null
    private var secondMarker: LatLng? = null

    private var onMarkerClick = OnMarkerClickListener { marker ->
        if (!state) {
            firstMarker = marker.position
            state = true
            Toast.makeText(context, "first_added", Toast.LENGTH_SHORT).show()
        } else {
            secondMarker = marker.position
            state = false
            Toast.makeText(context, "Second_added", Toast.LENGTH_SHORT).show()
        }
        if (firstMarker != null && secondMarker != null) {
            mapViewModel.getRoute(firstMarker!!, secondMarker!!).observe(this, {
                route(it, gmap)
                firstMarker = null
                secondMarker = null
            })
        }
        true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mapViewModel = ViewModelProvider(this, object : ViewModelMapFactory(context) {}).get(MapViewModel::class.java)
        if (arguments?.getString("id") != null) {
            id = arguments?.getString("id").toString()
            mapViewModel.getContactMarker(id).observe(this, {
                markers.addAll(it)
            })
        } else {
            id = ""
            mapViewModel.getContactMarkers().observe(this, {
                markers.addAll(it)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapTag = view.findViewById(R.id.textView)
        mapTag2 = view.findViewById(R.id.textView2)
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        gmap = p0
        val ny = LatLng(0.0, 0.0)
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny))
        gmap.uiSettings.setAllGesturesEnabled(true)
        for (i in markers.indices) {
            gmap.addMarker(MarkerOptions()
                    .position(LatLng(markers[i].latitude, markers[i].longitude)))
        }
        gmap.setOnMarkerClickListener(onMarkerClick)

    }

    override fun onResume() {
        mapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun route(mPoints: List<LatLng>, googleMap: GoogleMap) {
        if (mPoints.isEmpty()) {
            Snackbar.make(requireView(), "Route not possible", Snackbar.LENGTH_SHORT).show()
        } else {
            val line = PolylineOptions()
            line.width(4f).color(R.color.Font)
            val latLngBuilder = LatLngBounds.Builder()
            for (i in mPoints.indices) {
                line.add(mPoints.get(i))
                latLngBuilder.include(mPoints.get(i))
            }
            googleMap.addPolyline(line)
        }

    }

}