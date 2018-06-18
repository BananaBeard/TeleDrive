package com.kovalenko.teledrive.fragments.listfragments.driver

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.detailactivity.DriverDetailActivity
import com.kovalenko.teledrive.fragments.DialogDeleteItem
import com.kovalenko.teledrive.fragments.listfragments.ItemListFragment
import com.kovalenko.teledrive.models.Driver
import com.kovalenko.teledrive.viewholder.DriverViewHolder

abstract class DriverListFragment : ItemListFragment() {
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Driver, DriverViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var rootView = inflater.inflate(R.layout.fragment_driver_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        mRecycler = rootView.findViewById(R.id.drivers_list)
        mRecycler.setHasFixedSize(false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mManager = LinearLayoutManager(activity)
        mManager.reverseLayout = true
        mManager.stackFromEnd = true
        mRecycler.layoutManager = mManager

        var driversQuery = getQuery(mDatabase)

        var options = FirebaseRecyclerOptions.Builder<Driver>()
                .setQuery(driversQuery, Driver::class.java)
                .build()

        mAdapter = object : FirebaseRecyclerAdapter<Driver, DriverViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
                var inflater = LayoutInflater.from(parent!!.context)

                return DriverViewHolder(inflater.inflate(R.layout.item_driver, parent, false))
            }

            override fun onBindViewHolder(holder: DriverViewHolder, position: Int, model: Driver) {

                val driverRef = getRef(position)
                val driverKey = driverRef.key

                holder.itemView.setOnClickListener {
                    val intent = Intent(activity, DriverDetailActivity::class.java)
                    intent.putExtra(DriverDetailActivity.EXTRA_DRIVER_KEY, driverKey)
                    startActivity(intent)
                }

                holder.itemView.isLongClickable = true

                holder.itemView.setOnLongClickListener {
                    var dialog = DialogDeleteItem()
                    dialog.onAcceptListener = {driverRef.removeValue()}
                    dialog.show(childFragmentManager, "777")

                    true
                }
                holder.bindToDriver(model)
            }

        }
        mRecycler.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()

        if (mAdapter != null) {
            mAdapter.startListening()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onStop() {
        super.onStop()

        if (mAdapter != null) {
            mAdapter.stopListening()
        }
    }

    companion object {
        private val TAG = "DriverListFragment"
    }
}