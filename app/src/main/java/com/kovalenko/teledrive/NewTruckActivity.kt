package com.kovalenko.teledrive

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_truck.*

class NewTruckActivity: AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_truck)

        mDatabase = FirebaseDatabase.getInstance().reference
        fab_submit_truck.setOnClickListener {
            submitTruck()
        }

        var values = arrayOfNulls<String>(30)

        for (i in 1990..2019) {
            values[i-1990] = i.toString()
        }

        with(traktor_year_picker) {
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

    private fun submitTruck() {

        val truckBrand = field_truck_brand.text.toString()
        val truckModel = field_truck_model.text.toString()
    }

    fun addNewTruck() {

    }

    companion object {
        private val TAG = "NewTruckActivity"
    }
}