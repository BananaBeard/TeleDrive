package com.kovalenko.teledrive.fragments.listfragments.facility

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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.FacilityDetailActivity
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.fragments.DialogDeleteItem
import com.kovalenko.teledrive.fragments.listfragments.ItemListFragment
import com.kovalenko.teledrive.models.Facility
import com.kovalenko.teledrive.viewholder.FacilityViewHolder

class FacilityListFragment: ItemListFragment() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Facility, FacilityViewHolder>
    private lateinit var mRecycler: RecyclerView
    private lateinit var mManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var rootView = inflater.inflate(R.layout.fragment_facility_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        mRecycler = rootView.findViewById(R.id.facilities_list)
        mRecycler.setHasFixedSize(false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mManager = LinearLayoutManager(activity)
        mManager.reverseLayout = true
        mManager.stackFromEnd = true
        mRecycler.layoutManager = mManager

        var facilitiesQuery = getQuery(mDatabase)

        var options = FirebaseRecyclerOptions.Builder<Facility>()
                .setQuery(facilitiesQuery, Facility::class.java)
                .build()

        mAdapter = object: FirebaseRecyclerAdapter<Facility, FacilityViewHolder>(options) {

            override fun onBindViewHolder(holder: FacilityViewHolder, position: Int, model: Facility) {

                val facilityRef = getRef(position)
                val facilityKey = facilityRef.key

                holder!!.itemView.setOnClickListener {
                    val intent = Intent(activity, FacilityDetailActivity::class.java)
                    intent.putExtra(FacilityDetailActivity.EXTRA_FACILITY_KEY, facilityKey)
                    startActivity(intent)
                }

                holder.itemView.isLongClickable = true

                holder.itemView.setOnLongClickListener {

                    var dialog = DialogDeleteItem()
                    dialog.onAcceptListener = {facilityRef.removeValue()}

                    dialog.show(childFragmentManager, "777")

                    true
                }
                holder.bindToFacility(model)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
                var inflater = LayoutInflater.from(parent!!.context)

                return FacilityViewHolder(inflater.inflate(R.layout.item_facility, parent, false))
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
        mAdapter.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        if (mAdapter != null) {
            mAdapter.stopListening()
        }
    }

    override fun getQuery(databaseReference: DatabaseReference): Query {

        var uId = getUid()

        var allFacilitiesQuery = databaseReference.child(uId).child("facilities")
                .limitToFirst(1000)

        return allFacilitiesQuery
    }

    companion object {
        private val TAG = "FacilityListFragment"
    }
}