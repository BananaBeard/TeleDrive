package com.kovalenko.teledrive.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Truck
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_new_truck.*

class NewTruckActivity: AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_truck)

        mDatabase = FirebaseDatabase.getInstance().reference

        fab_submit_truck.setOnClickListener {
            submitNewTruck()
        }

        var values = arrayOfNulls<String>(30)

        for (i in 1990..2019) {
            values[i-1990] = i.toString()
        }

        with(tractor_year_picker) {
            maxValue = values[values.size-1]!!.toInt()
            minValue = values[0]!!.toInt()
            displayedValues = values
        }

        with(reefer_year_picker) {
            maxValue = values[values.size-1]!!.toInt()
            minValue = values[0]!!.toInt()
            displayedValues = values
        }
    }

    private fun submitNewTruck() {

        val truckBrand = field_truck_brand.text.toString()
        val truckModel = field_truck_model.text.toString()

        val tractorYear = tractor_year_picker.value
        val reeferYear = reefer_year_picker.value

        val userId = getUid()

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "getUser:onCancelled", p0!!.toException())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                var user = p0!!.getValue(User::class.java)

                if (user == null) {
                    Log.e(TAG, "User $userId is unexpectedly null")
                    Toast.makeText(this@NewTruckActivity,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show()
                } else {
                    addNewTruck(userId, truckBrand, truckModel, tractorYear, reeferYear)
                }
                finish()
            }
        })
    }

    private fun validateNewTruck() : Boolean {

        return true
    }

    private fun addNewTruck(
            userId: String,
            truckBrand: String,
            truckModel: String,
            tractorYear: Int,
            reeferYear: Int
    ) {
        var key = mDatabase.child("trucks").push().key
        var truck = Truck(truckBrand, truckModel, tractorYear, reeferYear)

        mDatabase.child("trucks").child(key).setValue(truck)
    }

    companion object {
        private val TAG = "NewTruckActivity"
    }
}