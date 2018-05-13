package com.kovalenko.teledrive.fragments.listfragments.truck

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.TruckDetailActivity
import com.kovalenko.teledrive.models.Truck
import com.kovalenko.teledrive.viewholder.TruckViewHolder
import kotlinx.android.synthetic.main.fragment_truck_list.*

abstract class TruckListFragment: Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Truck, TruckViewHolder>
    private lateinit var mRecycler: RecyclerView
    private lateinit var mManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var rootView = inflater.inflate(R.layout.fragment_truck_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        mRecycler = rootView.findViewById(R.id.trucks_list)
        mRecycler.setHasFixedSize(false)

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mManager = LinearLayoutManager(activity)
        mManager.reverseLayout = true
        mManager.stackFromEnd = true
        mRecycler.layoutManager = mManager

        var trucksQuery = getQuery(mDatabase)

        var options = FirebaseRecyclerOptions.Builder<Truck>()
                .setQuery(trucksQuery, Truck::class.java)
                .build()

        mAdapter = object: FirebaseRecyclerAdapter<Truck, TruckViewHolder>(options) {
            override fun onBindViewHolder(holder: TruckViewHolder?, position: Int, model: Truck?) {
                val truckRef = getRef(position)
                val truckKey = truckRef.key

                holder!!.itemView.setOnClickListener {
                    val intent = Intent(activity, TruckDetailActivity::class.java)
                    intent.putExtra(TruckDetailActivity.EXTRA_TRUCK_KEY, truckKey)
                    startActivity(intent)
                }
                holder.bindToTruck(model!!)
            }

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TruckViewHolder {
                var inflater = LayoutInflater.from(parent!!.context)

                return TruckViewHolder(inflater.inflate(R.layout.item_truck, parent, false))
            }
        }
        mRecycler.adapter = mAdapter

        swipe_refresh_trucks.setOnRefreshListener {
            mAdapter.notifyDataSetChanged()
            swipe_refresh_trucks.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAdapter != null) {
            mAdapter.startListening()
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        if (mAdapter != null) {
            mAdapter.stopListening()
        }
    }

    fun getUid() = FirebaseAuth.getInstance().currentUser!!.uid

    abstract fun getQuery(databaseReference: DatabaseReference): Query

    companion object {
        private val TAG = "TruckListFragment"
    }
}