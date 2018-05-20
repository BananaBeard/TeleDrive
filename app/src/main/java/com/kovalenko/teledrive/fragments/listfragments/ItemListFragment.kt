package com.kovalenko.teledrive.fragments.listfragments

import android.support.v4.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

abstract class ItemListFragment: Fragment() {
    abstract fun getQuery(databaseReference: DatabaseReference): Query
}
