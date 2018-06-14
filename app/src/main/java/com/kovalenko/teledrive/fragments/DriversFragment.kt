package com.kovalenko.teledrive.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.newactivity.NewDriverActivity

import kotlinx.android.synthetic.main.fragment_drivers.*

class DriversFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_drivers, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_new_driver.setOnClickListener {
            val intent = Intent(activity, NewDriverActivity::class.java)
            startActivity(intent)
        }
    }
}