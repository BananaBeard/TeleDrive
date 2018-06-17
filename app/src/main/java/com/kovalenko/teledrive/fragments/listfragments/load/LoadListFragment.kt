package com.kovalenko.teledrive.fragments.listfragments.load

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
import com.kovalenko.teledrive.activity.detailactivity.LoadDetailActivity
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.fragments.DialogDeleteItem
import com.kovalenko.teledrive.fragments.listfragments.ItemListFragment
import com.kovalenko.teledrive.models.Load
import com.kovalenko.teledrive.viewholder.LoadViewHolder

abstract class LoadListFragment: ItemListFragment() {

    private lateinit var mAdapter: FirebaseRecyclerAdapter<Load, LoadViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        var rootView = inflater.inflate(R.layout.fragment_load_list, container, false)

        mDatabase = FirebaseDatabase.getInstance().reference
        mRecycler = rootView.findViewById(R.id.loads_list)
        mRecycler.setHasFixedSize(true)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mManager = LinearLayoutManager(activity)
        mManager.reverseLayout = true
        mManager.stackFromEnd = true
        mRecycler.layoutManager = mManager

        var loadsQuery = getQuery(mDatabase)

        var options = FirebaseRecyclerOptions.Builder<Load>()
                .setQuery(loadsQuery, Load::class.java)
                .build()

        mAdapter = object: FirebaseRecyclerAdapter<Load, LoadViewHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
                var inflater = LayoutInflater.from(parent!!.context)

                return LoadViewHolder(inflater.inflate(R.layout.item_load, parent, false))
            }

            override fun onBindViewHolder(holder: LoadViewHolder, position: Int, model: Load) {

                val loadRef = getRef(position)
                val loadKey = loadRef.key

                holder!!.itemView.setOnClickListener {
                    var intent = Intent(activity, LoadDetailActivity::class.java)
                    intent.putExtra(LoadDetailActivity.EXTRA_LOAD_KEY, loadKey)
                    startActivity(intent)
                }

                holder.itemView.setOnLongClickListener {

                    var dialog = DialogDeleteItem()
                    dialog.onAcceptListener = {loadRef.removeValue()}

                    dialog.show(childFragmentManager, "777")

                    true
                }

                holder.bindToLoad(model!!)
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

    override fun onStop() {
        super.onStop()
        if (mAdapter != null) {
            mAdapter.stopListening()
        }
    }

    companion object {
        private val TAG = "LoadListFragment"
    }
}