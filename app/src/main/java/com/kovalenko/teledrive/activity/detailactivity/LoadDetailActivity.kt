package com.kovalenko.teledrive.activity.detailactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kovalenko.teledrive.R
import kotlinx.android.synthetic.main.activity_load_detail.*

class LoadDetailActivity: DetailActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_detail)

        load_detail_layout.background.alpha = 50
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
        private val TAG = "LoadDetailActivity"
        public val EXTRA_LOAD_KEY = "load_key"
    }
}