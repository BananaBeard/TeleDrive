package com.kovalenko.teledrive.fragments.listfragments.truck

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Truck
import com.kovalenko.teledrive.viewholder.TruckViewHolder

abstract class TruckListFragment: Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Truck, TruckViewHolder>
    private lateinit var mRecycler: RecyclerView
    private lateinit var mManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var rootView = inflater.inflate(R.layout.fragment_truck_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        mRecycler = rootView.findViewById(R.id.loads_list)
        mRecycler.setHasFixedSize(true)

        return rootView
    }
}