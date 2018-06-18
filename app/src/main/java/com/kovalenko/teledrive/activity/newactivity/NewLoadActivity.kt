package com.kovalenko.teledrive.activity.newactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Load
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_new_load.*

class NewLoadActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
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
        setContentView(R.layout.activity_new_load)

        new_load_layout.background.alpha = 50

        mDatabase = FirebaseDatabase.getInstance().reference

        mFacilitiesReference = mDatabase.child("facilities").child(getUid())
        mDriversReference = mDatabase.child("drivers").child(getUid())
        mTrucksReference = mDatabase.child("trucks").child(getUid())

        fab_submit_load.setOnClickListener {
            submitLoad()
        }

        var loadtypeAdapter = ArrayAdapter<String>(
                this@NewLoadActivity,
                android.R.layout.simple_spinner_item,
                listOf("Рифер", "Бортовий")
        )
        loadtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_load_type.adapter = loadtypeAdapter
    }

    override fun onStart() {
        super.onStart()

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
                        this@NewLoadActivity,
                        android.R.layout.simple_spinner_item,
                        facilityList
                )
                facilitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_load_shipper.adapter = facilitiesAdapter
                spinner_load_consignee.adapter = facilitiesAdapter
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
                        this@NewLoadActivity,
                        android.R.layout.simple_spinner_item,
                        driverList
                )
                driversAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_load_driver.adapter = driversAdapter
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
                        this@NewLoadActivity,
                        android.R.layout.simple_spinner_item,
                        truckList
                )
                trucksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_load_truck.adapter = trucksAdapter
            }
        }

        mFacilitiesReference.addValueEventListener(facilitiesListener)
        mDriversReference.addValueEventListener(driversListener)
        mTrucksReference.addValueEventListener(trucksListener)
        mFacilitiesListener = facilitiesListener
        mDriversListener = driversListener
        mTrucksListener = trucksListener
    }

    override fun onStop() {
        super.onStop()
        mFacilitiesReference.removeEventListener(mFacilitiesListener)
        mDriversReference.removeEventListener(mDriversListener)
        mTrucksReference.removeEventListener(mTrucksListener)
    }

    private fun submitLoad() {

        if (!validateNewLoad()) {
            return
        }

        val loadId = input_load_id.text.toString().trim()
        val customer = input_customer_name.text.toString().trim()
        val customerRate = input_customer_rate.text.toString().trim().toDouble()
        val driverRate = input_driver_rate.text.toString().trim().toDouble()
        val loadType = spinner_load_type.selectedItem.toString()
        val loadPieces = input_load_pieces.text.toString().trim().toInt()
        val shipperId = facilitiesMap[spinner_load_shipper.selectedItem.toString()]
        val consigneeId = facilitiesMap[spinner_load_consignee.selectedItem.toString()]
        val driverId = driversMap[spinner_load_driver.selectedItem.toString()]
        val truckId = trucksMap[spinner_load_truck.selectedItem.toString()]

        val userId = getUid()

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                var user = p0!!.getValue(User::class.java)

                if (user == null) {
                    Log.e(TAG, "User $userId is unexpectedly null")
                    Toast.makeText(this@NewLoadActivity,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show()
                } else {
                    addNewLoad(loadId, customer,
                            customerRate, driverRate,
                            loadType, loadPieces,
                            shipperId!!, consigneeId!!,
                            driverId!!, truckId!!)
                }
                finish()
            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "getUser:onCancelled", p0!!.toException())
            }
        })
    }

    fun addNewLoad(
            loadId: String,
            customer: String,
            customerRate: Double,
            driverRate: Double,
            type: String,
            pieces: Int,
            shipper: String,
            consignee: String,
            driver: String,
            truck: String
    ) {
        var key = mDatabase.child("loads").child(getUid()).push().key
        var load = Load(loadId, customer, customerRate, driverRate, type, pieces, shipper, consignee, driver, truck, "Active")

        mDatabase.child("loads").child(getUid()).child(key).setValue(load)

        mDriversReference.child(driver).child("working").setValue(true)
        mTrucksReference.child(truck).child("used").setValue(true)

        mDriversReference.child(driver).child("load").setValue(loadId)
        mTrucksReference.child(truck).child("load").setValue(loadId)
    }

    fun validateNewLoad() : Boolean {
        var result = true

        if (input_load_id.text.isEmpty()) {
            input_load_id.error = "Required"
            result = false
        }
        if (input_customer_name.text.isEmpty()) {
            input_customer_name.error = "Required"
            result = false
        }
        if (input_customer_rate.text.isEmpty()) {
            input_customer_rate.error = "Required"
            result = false
        }
        if (input_driver_rate.text.isEmpty()) {
            input_driver_rate.error = "Required"
            result = false
        }
        if (input_load_pieces.text.isEmpty()) {
            input_load_pieces.error = "Required"
            result = false
        }

        return result
    }

    companion object {
        private val TAG = "NewLoadActivity"
    }
}
