package com.kovalenko.teledrive.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Facility
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_new_facility.*

class NewFacilityActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_facility)

        mDatabase = FirebaseDatabase.getInstance().reference

        fab_submit_facility.setOnClickListener {
            submitNewFacility()
        }

        button_pick_address.setOnClickListener {
            pickPlace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                var place = PlacePicker.getPlace(this, data)
                var address = String.format("%s", place.address)

                input_facility_address.setText(address)
            }
        }
    }

    private fun submitNewFacility() {

        if (!validateNewFacility()) {
            return
        }

        var userId = getUid()

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "getUser:onCancelled", p0!!.toException())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                var user = p0!!.getValue(User::class.java)

                if (user == null) {
                    Log.e(TAG, "User $userId is unexpectedly null")
                    Toast.makeText(this@NewFacilityActivity,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show()
                } else {
                    addNewFacility(userId, input_facility_name.text.toString(), input_facility_address.text.toString())
                }
                finish()
            }
        })
    }

    private fun addNewFacility(userId: String, name: String, address: String) {

        var key = mDatabase.child(userId).child("facilities").push().key
        var facility = Facility(name, address)

        mDatabase.child(userId).child("facilities").child(key).setValue(facility)
    }

    fun validateNewFacility(): Boolean {

        var result = true

        if (input_facility_name.text.isEmpty()) {
            input_facility_name.error = "Required"
            result = false
        }

        if (input_facility_address.text.isEmpty()) {
            input_facility_address.error = "Required"
            result = false
        }

        return result
    }

    private fun pickPlace() {
        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this@NewFacilityActivity), PLACE_PICKER_REQUEST)
    }

    companion object {
        private val PLACE_PICKER_REQUEST = 1
        private val TAG = "NewFacilityActivity"
    }
}
