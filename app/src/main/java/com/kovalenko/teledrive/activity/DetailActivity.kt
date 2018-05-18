package com.kovalenko.teledrive.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

abstract class DetailActivity : AppCompatActivity() {

    protected val ANIM_DURATION = 750L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun submitChanges()

    abstract fun enableEdit(switch: Boolean)

    abstract fun validateChanges(): Boolean
}
