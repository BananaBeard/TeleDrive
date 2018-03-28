package com.kovalenko.teledrive.fragments.listfragments.truck

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class AllTrucksFragment : TruckListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var allTrucksQuery = databaseReference.child("trucks")
                .limitToFirst(1000)

        return allTrucksQuery
    }
}