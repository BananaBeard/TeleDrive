package com.kovalenko.teledrive.fragments.listfragments.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.kovalenko.teledrive.fragments.listfragments.ItemListFragment
import com.kovalenko.teledrive.models.Driver
import com.kovalenko.teledrive.viewholder.DriverViewHolder

abstract class DriverListFragment : ItemListFragment() {
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Driver, DriverViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        private val TAG = "DriverListFragment"
    }
}