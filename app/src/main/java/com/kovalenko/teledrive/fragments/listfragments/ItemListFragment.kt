package com.kovalenko.teledrive.fragments.listfragments

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

abstract class ItemListFragment: Fragment() {

    protected lateinit var mDatabase: DatabaseReference
    protected lateinit var mRecycler: RecyclerView
    protected lateinit var mManager: LinearLayoutManager

    abstract fun getQuery(databaseReference: DatabaseReference): Query
}
