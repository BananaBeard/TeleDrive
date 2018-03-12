package com.kovalenko.teledrive.fragments.listfragments.load

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class AllLoadsFragment: LoadListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var allLoadsQuery = databaseReference.child("loads")
                .limitToFirst(1000)

        return allLoadsQuery
    }
}