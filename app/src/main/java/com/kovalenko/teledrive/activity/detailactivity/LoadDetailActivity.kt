package com.kovalenko.teledrive.activity.detailactivity

import android.support.v7.app.AppCompatActivity

class LoadDetailActivity: DetailActivity() {
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