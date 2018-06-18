package com.kovalenko.teledrive.activity

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.kovalenko.teledrive.R
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap

    private var places = ArrayList<LatLng>()

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        button_draw.setOnClickListener {

            val geoApiContext = GeoApiContext.Builder().apiKey("AIzaSyCphOvZgrSIJFxVLqytWib82pin6cfanzc").build()

            var result = DirectionsApi.newRequest(geoApiContext)
                    .origin(com.google.maps.model.LatLng(places[0].latitude, places[0].longitude))
                    .destination(com.google.maps.model.LatLng(places[places.size-1].latitude, places[places.size-1].longitude))
                    .waypoints(
                            com.google.maps.model.LatLng(places[1].latitude, places[1].longitude),
                            com.google.maps.model.LatLng(places[0].latitude, places[2].longitude)
                    ).await()

            var path = result.routes[0].overviewPolyline.decodePath()

            var line = PolylineOptions()
            var latLngBuilder = LatLngBounds.Builder()

            for (i in path) {
                line.add(LatLng(i.lat, i.lng))
                latLngBuilder.include(LatLng(i.lat, i.lng))
            }

            line.width(16F).color(R.color.colorAccent)
            mMap.addPolyline(line)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMapLongClickListener(this)

        val kiev = LatLng(50.431782, 30.516382)
        mMap.addMarker(MarkerOptions().position(kiev).title("Marker in Kiev"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kiev))
        mMap.setMinZoomPreference(10.0f)
        mMap.setMaxZoomPreference(14.0f)

    }


    override fun onMapLongClick(p0: LatLng?) {
        var vibe = this.getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibe.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibe.vibrate(50)
        }

        var marker = mMap.addMarker(MarkerOptions()
                .position(p0!!)
                .title("$counter"))

        places.add(LatLng(marker.position.latitude, marker.position.longitude))

        counter++
    }
}
