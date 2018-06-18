package com.kovalenko.teledrive.activity.newactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Truck
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_new_truck.*

class NewTruckActivity: AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_truck)

        new_truck_layout.background.alpha = 50

        mDatabase = FirebaseDatabase.getInstance().reference

        fab_submit_truck.setOnClickListener {
            submitNewTruck()
        }

        var values = arrayOfNulls<String>(30)
    }

    private fun submitNewTruck() {

        val truckBrand = input_truck_brand.text.toString().trim()
        val truckModel = edit_truck_model.text.toString().trim()

        val tractorYear = input_tractor_year.text.toString().trim().toInt()
        val reeferYear = input_reefer_year.text.toString().trim().toInt()

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
        var key = mDatabase.child("trucks").child(userId).push().key
        var truck = Truck(truckBrand, truckModel, tractorYear, reeferYear)

        mDatabase.child("trucks").child(userId).child(key).setValue(truck)
    }

    companion object {
        private val TAG = "NewTruckActivity"
    }
}