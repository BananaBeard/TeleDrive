package com.kovalenko.teledrive.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

abstract class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun submitChanges()
}
