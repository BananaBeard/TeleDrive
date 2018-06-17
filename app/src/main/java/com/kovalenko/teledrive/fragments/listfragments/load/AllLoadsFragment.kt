package com.kovalenko.teledrive.fragments.listfragments.load

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class AllLoadsFragment: LoadListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var allLoadsQuery = databaseReference.child("loads").child(getUid())
                .limitToFirst(1000)

        return allLoadsQuery
    }
}