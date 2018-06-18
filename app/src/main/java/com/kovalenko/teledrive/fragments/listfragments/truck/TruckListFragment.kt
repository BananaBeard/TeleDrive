package com.kovalenko.teledrive.fragments.listfragments.truck

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.detailactivity.TruckDetailActivity
import com.kovalenko.teledrive.fragments.DialogDeleteItem
import com.kovalenko.teledrive.fragments.listfragments.ItemListFragment
import com.kovalenko.teledrive.models.Truck
import com.kovalenko.teledrive.viewholder.TruckViewHolder
import kotlinx.android.synthetic.main.fragment_truck_list.*

abstract class TruckListFragment: ItemListFragment() {

    private lateinit var mAdapter: FirebaseRecyclerAdapter<Truck, TruckViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var rootView = inflater.inflate(R.layout.fragment_truck_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        mRecycler = rootView.findViewById(R.id.trucks_list)
        mRecycler.setHasFixedSize(false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

            override fun onBindViewHolder(holder: TruckViewHolder, position: Int, model: Truck) {
                val truckRef = getRef(position)
                val truckKey = truckRef.key

                holder!!.itemView.setOnClickListener {
                    val intent = Intent(activity, TruckDetailActivity::class.java)
                    intent.putExtra(TruckDetailActivity.EXTRA_TRUCK_KEY, truckKey)
                    startActivity(intent)
                }

                holder.itemView.isLongClickable = true

                holder.itemView.setOnLongClickListener {

                    var dialog = DialogDeleteItem()
                    dialog.onAcceptListener = {truckRef.removeValue()}

                    dialog.show(childFragmentManager, "777")

                    true
                }
                holder.bindToTruck(model)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TruckViewHolder {
                var inflater = LayoutInflater.from(parent!!.context)

                return TruckViewHolder(inflater.inflate(R.layout.item_truck, parent, false))
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

    companion object {
        private val TAG = "TruckListFragment"
    }
}