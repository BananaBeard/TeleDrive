package com.kovalenko.teledrive.activity.detailactivity

import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Load
import kotlinx.android.synthetic.main.activity_load_detail.*
import java.util.*
import kotlin.collections.ArrayList

class LoadDetailActivity: DetailActivity(), OnMapReadyCallback {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mLoadReference: DatabaseReference
    private lateinit var mLoadListener: ValueEventListener
    private lateinit var mLoadKey: String
    private var mMap: GoogleMap? = null
    private lateinit var locationMarker: Marker

    private lateinit var mFacilitiesListener: ValueEventListener
    private lateinit var mDriversListener: ValueEventListener
    private lateinit var mTrucksListener: ValueEventListener
    private lateinit var mFacilitiesReference: DatabaseReference
    private lateinit var mDriversReference: DatabaseReference
    private lateinit var mTrucksReference: DatabaseReference
    private lateinit var facilitiesMap: HashMap<String, String>
    private lateinit var driversMap: HashMap<String, String>
    private lateinit var trucksMap: HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_detail)

        load_detail_layout.background.alpha = 50

        edit_load_id.inputType = InputType.TYPE_NULL
        edit_customer_name.inputType = InputType.TYPE_NULL
        edit_customer_rate.inputType = InputType.TYPE_NULL
        edit_driver_rate.inputType = InputType.TYPE_NULL
        edit_load_pieces.inputType = InputType.TYPE_NULL
        spinner_detail_load_type.isEnabled = false
        spinner_detail_load_shipper.isEnabled = false
        spinner_detail_load_consignee.isEnabled = false
        spinner_detail_load_driver.isEnabled = false
        spinner_detail_load_truck.isEnabled = false

        mLoadKey = intent.getStringExtra(EXTRA_LOAD_KEY)

        mDatabase = FirebaseDatabase.getInstance().reference
        mLoadReference = mDatabase.child("loads").child(getUid()).child(mLoadKey)
        mFacilitiesReference = mDatabase.child("facilities").child(getUid())
        mDriversReference = mDatabase.child("drivers").child(getUid())
        mTrucksReference = mDatabase.child("trucks").child(getUid())

        with(switch_edit_load){
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
        }

        switch_edit_load.setOnCheckedChangeListener{ _: CompoundButton, b: Boolean ->
            animateSwitch(switch_edit_load)
            enableEdit(b)
        }

        fab_edit_load.setOnClickListener {
            submitChanges()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.load_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()

        var loadListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var load = p0!!.getValue(Load::class.java)

                edit_load_id.setText(load!!.loadId)
                edit_customer_name.setText(load.customer)
                edit_customer_rate.setText(load.customerRate.toString())
                edit_driver_rate.setText(load.driverRate.toString())
                edit_load_pieces.setText(load.pieces.toString())

                if (mMap!= null) {
      //              drawRoute(load.shipper, load.consignee)
                    locationMarker.position = LatLng(load.latitude, load.longitude)
                    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(locationMarker.position))
                }


//                spinner_detail_load_type.setSelection()
//                spinner_detail_load_shipper.isEnabled = false
//                spinner_detail_load_consignee.isEnabled = false
//                spinner_detail_load_driver.isEnabled = false
//                spinner_detail_load_truck.isEnabled = false
            }
        }

        var facilitiesListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var facilityList = ArrayList<String>()
                facilitiesMap = HashMap()

                for (snapshot in p0!!.children) {
                    var facilityName = snapshot.child("facilityName").getValue(String::class.java)

                    facilityList.add(facilityName!!)
                    facilitiesMap[facilityName!!] = snapshot.key
                }

                var facilitiesAdapter = ArrayAdapter<String>(
                        this@LoadDetailActivity,
                        android.R.layout.simple_spinner_item,
                        facilityList
                )
                facilitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_detail_load_shipper.adapter = facilitiesAdapter
                spinner_detail_load_consignee.adapter = facilitiesAdapter
            }
        }

        var driversListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var driverList = ArrayList<String>()
                driversMap = HashMap()

                for (snapshot in p0!!.children) {
                    var firstName = snapshot.child("firstName").getValue(String::class.java)
                    var secondName = snapshot.child("secondName").getValue(String::class.java)

                    driverList.add("$firstName $secondName")
                    driversMap["$firstName $secondName"] = snapshot.key
                }

                var driversAdapter = ArrayAdapter<String>(
                        this@LoadDetailActivity,
                        android.R.layout.simple_spinner_item,
                        driverList
                )
                driversAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_detail_load_driver.adapter = driversAdapter
            }
        }

        var trucksListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var truckList = ArrayList<String>()
                trucksMap = HashMap()

                for (snapshot in p0!!.children) {
                    var brand = snapshot.child("brand").getValue(String::class.java)
                    var model = snapshot.child("model").getValue(String::class.java)

                    truckList.add("$brand $model")
                    trucksMap["$brand $model"] = snapshot.key
                }

                var trucksAdapter = ArrayAdapter<String>(
                        this@LoadDetailActivity,
                        android.R.layout.simple_spinner_item,
                        truckList
                )
                trucksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_detail_load_truck.adapter = trucksAdapter
            }
        }

        mFacilitiesReference.addValueEventListener(facilitiesListener)
        mDriversReference.addValueEventListener(driversListener)
        mTrucksReference.addValueEventListener(trucksListener)
        mLoadReference.addValueEventListener(loadListener)
        mFacilitiesListener = facilitiesListener
        mDriversListener = driversListener
        mTrucksListener = trucksListener
        mLoadListener = loadListener
    }

    override fun onStop() {
        super.onStop()

        mFacilitiesReference.removeEventListener(mFacilitiesListener)
        mDriversReference.removeEventListener(mDriversListener)
        mTrucksReference.removeEventListener(mTrucksListener)
        mLoadReference.removeEventListener(mLoadListener)
    }

    override fun submitChanges() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enableEdit(switch: Boolean) {
        if (switch) {
            edit_load_id.inputType = InputType.TYPE_CLASS_NUMBER
            edit_customer_name.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            edit_customer_rate.inputType = InputType.TYPE_CLASS_NUMBER
            edit_driver_rate.inputType = InputType.TYPE_CLASS_NUMBER
            edit_load_pieces.inputType = InputType.TYPE_CLASS_NUMBER
            spinner_detail_load_type.isEnabled = true
            spinner_detail_load_shipper.isEnabled = true
            spinner_detail_load_consignee.isEnabled = true
            spinner_detail_load_driver.isEnabled = true
            spinner_detail_load_truck.isEnabled = true
        } else {
            edit_load_id.inputType = InputType.TYPE_NULL
            edit_customer_name.inputType = InputType.TYPE_NULL
            edit_customer_rate.inputType = InputType.TYPE_NULL
            edit_driver_rate.inputType = InputType.TYPE_NULL
            edit_load_pieces.inputType = InputType.TYPE_NULL
            spinner_detail_load_type.isEnabled = false
            spinner_detail_load_shipper.isEnabled = false
            spinner_detail_load_consignee.isEnabled = false
            spinner_detail_load_driver.isEnabled = false
            spinner_detail_load_truck.isEnabled = false
        }
    }

    override fun validateChanges(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap!!.setMinZoomPreference(16.0f)

        locationMarker = mMap!!.addMarker(MarkerOptions().position(LatLng(0.0, 0.0)).title("Load"))
    }

//    fun drawRoute(ship: String, cons: String) {
//        lateinit var consAddress: String
//
//        mFacilitiesReference.child(ship).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError?) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
//                var address = p0!!.child("address").getValue(String::class.java)
//                var shipperAddress = address!!
//            }
//        })
//
//        mFacilitiesReference.child(cons).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError?) {
//
//            }
//
//            override fun onDataChange(p0: DataSnapshot?) {
//                var address = p0!!.child("address").getValue(String::class.java)
//                consAddress = address!!
//            }
//        })
//
//        var stops = ArrayList<LatLng>()
//
//        var geocoder = Geocoder(this, Locale("Ukrainian"))
//        var shipGeo = geocoder.getFromLocationName(shipperAddress, 1)
//        var consGeo = geocoder.getFromLocationName(consAddress, 1)
//
//        if (shipGeo.size > 0 && consGeo.size > 0) {
//            stops.add(LatLng(shipGeo[0].latitude,shipGeo[0].longitude))
//            stops.add(LatLng(consGeo[0].latitude,consGeo[0].longitude))
//        }
//
//        var markers = arrayOf(MarkerOptions().position(stops[0]), MarkerOptions().position(stops[1]))
//        mMap!!.addMarker(markers[0])
//        mMap!!.addMarker(markers[1])
//
//        var geoapi = GeoApiContext.Builder().apiKey("AIzaSyCYGk6GHQD_vSLJ2e-NCVBcWIze8SXqkIE").build()
//
//        var result = DirectionsApi.newRequest(geoapi)
//                .origin(com.google.maps.model.LatLng(shipGeo[0].latitude,shipGeo[0].longitude))
//                .destination(com.google.maps.model.LatLng(consGeo[0].latitude,consGeo[0].longitude))
//                .await()
//
//        var path = result.routes[0].overviewPolyline.decodePath()
//
//        var line = PolylineOptions()
//        var latLngBuilder = LatLngBounds.Builder()
//
//        for (i in path) {
//            line.add(LatLng(i.lat, i.lng))
//            latLngBuilder.include(LatLng(i.lat, i.lng))
//        }
//
//        line.width(16F).color(R.color.colorAccent)
//        mMap!!.addPolyline(line)
//    }

    companion object {
        private val TAG = "LoadDetailActivity"
        public val EXTRA_LOAD_KEY = "load_key"
    }
}