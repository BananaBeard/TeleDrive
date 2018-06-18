package com.kovalenko.teledrive.activity.detailactivity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.Geocoder
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.firebase.database.*
import com.google.android.gms.maps.model.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Facility
import kotlinx.android.synthetic.main.activity_facility_detail.*
import java.util.*

class FacilityDetailActivity: DetailActivity(), OnMapReadyCallback {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mFacilityReference: DatabaseReference
    private lateinit var mFacilityListener: ValueEventListener
    private lateinit var mFacilityKey: String
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility_detail)

        button_pick_address.alpha = 0.5F

        facility_detail_layout.background.alpha = 50

        edit_facility_name.inputType = InputType.TYPE_NULL
        edit_facility_address.inputType = InputType.TYPE_NULL

        mFacilityKey = intent.getStringExtra(EXTRA_FACILITY_KEY)
        mDatabase = FirebaseDatabase.getInstance().reference
        mFacilityReference = mDatabase.child("facilities").child(getUid()).child(mFacilityKey)

        with(switch_edit_facility) {
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
        }

        switch_edit_facility.setOnCheckedChangeListener { _, b ->
            animateSwitch(switch_edit_facility)
            enableEdit(b)
        }

        fab_edit_facility.setOnClickListener {
            submitChanges()
        }

        button_pick_address.setOnClickListener {
            pickPlace()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.facility_location_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()

        var facilityListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "loadFacility:onCancelled", p0!!.toException())

                Toast.makeText(this@FacilityDetailActivity, "Failed to load facility.",
                        Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot?) {
                var facility = p0!!.getValue(Facility::class.java)

                edit_facility_name.setText(facility!!.facilityName)
                edit_facility_address.setText(facility.address)

                setLocationOnMap(facility.address, facility.facilityName)
            }
        }

        mFacilityReference.addValueEventListener(facilityListener)
        mFacilityListener = facilityListener
    }

    override fun onStop() {
        super.onStop()

        mFacilityReference.removeEventListener(mFacilityListener)
    }

    override fun submitChanges() {

        if (validateChanges()) {
            val updateMap = HashMap<String, Any>()
            updateMap["facilityName"] = edit_facility_name.text.toString().trim()
            updateMap["address"] = edit_facility_address.text.toString().trim()
            mDatabase.child("facilities").child(getUid()).child(mFacilityKey).updateChildren(updateMap)
            finish()
        } else {
            return
        }
    }

    override fun validateChanges(): Boolean {
        var result = true

        if (edit_facility_name.text.isNullOrEmpty()) {
            edit_facility_name.error = "Required"
            result = false
        }
        if (edit_facility_address.text.isNullOrEmpty()) {
            edit_facility_address.error = "Required"
            result = false
        }

        return result
    }

    override fun enableEdit(switch: Boolean) {
        if (switch) {
            edit_facility_name.inputType = InputType.TYPE_CLASS_TEXT
            edit_facility_address.inputType = InputType.TYPE_CLASS_TEXT
            button_pick_address.isEnabled = true
            fab_edit_facility.visibility = View.VISIBLE
            button_pick_address.alpha = 1F
        } else {
            edit_facility_name.inputType = InputType.TYPE_NULL
            edit_facility_address.inputType = InputType.TYPE_NULL
            button_pick_address.isEnabled = false
            fab_edit_facility.visibility = View.INVISIBLE
            button_pick_address.alpha = 0.5F
        }
    }

    private fun pickPlace() {
        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this@FacilityDetailActivity), PLACE_PICKER_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                var place = PlacePicker.getPlace(this, data)
                var address = String.format("%s", place.address)

                edit_facility_address.setText(address)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setMinZoomPreference(14.0f)
    }

    fun setLocationOnMap(address: String, name: String) {
        var geocoder = Geocoder(this, Locale("Ukrainian"))
        var address = geocoder.getFromLocationName(address, 1)

        if (address.size > 0) {
            var facilityCoordinates = LatLng(address[0].latitude, address[0].longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(facilityCoordinates))
            mMap.addMarker(MarkerOptions().position(facilityCoordinates).title(name))
        }
    }

    companion object {
        private val TAG = "FacilityDetailActivity"
        const val EXTRA_FACILITY_KEY = "facility_key"
        private val PLACE_PICKER_REQUEST = 1
    }
}
