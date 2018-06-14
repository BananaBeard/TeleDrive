package com.kovalenko.teledrive.fragments.listfragments.truck

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class AllTrucksFragment : TruckListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var allTrucksQuery = databaseReference.child("trucks")
                .child(getUid())
                .limitToFirst(1000)

        return allTrucksQuery
    }
}