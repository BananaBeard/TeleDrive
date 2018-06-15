package com.kovalenko.teledrive.activity.detailactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kovalenko.teledrive.R
import kotlinx.android.synthetic.main.activity_driver_detail.*

class DriverDetailActivity : DetailActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_detail)

        driver_detail_layout.background.alpha = 50
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
}
