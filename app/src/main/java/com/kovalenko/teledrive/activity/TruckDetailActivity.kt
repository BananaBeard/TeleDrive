package com.kovalenko.teledrive.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kovalenko.teledrive.R

class TruckDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_detail)
    }

    companion object {
        private val TAG = "TruckDetailActivity"
        public val EXTRA_TRUCK_KEY = "truck_key"
    }
}
