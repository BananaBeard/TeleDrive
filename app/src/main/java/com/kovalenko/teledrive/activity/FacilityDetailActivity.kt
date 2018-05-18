package com.kovalenko.teledrive.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kovalenko.teledrive.R

class FacilityDetailActivity: DetailActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facility_detail)
    }

    override fun submitChanges() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enableEdit(switch: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun validateChanges(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        private val TAG = "FacilityDetailActivity"
        const val EXTRA_FACILITY_KEY = "facility_key"
    }
}
