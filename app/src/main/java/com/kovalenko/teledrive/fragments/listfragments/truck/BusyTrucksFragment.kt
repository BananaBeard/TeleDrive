package com.kovalenko.teledrive.fragments.listfragments.truck

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class BusyTrucksFragment : TruckListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var busyTrucksQuery = databaseReference.child("trucks").orderByChild("used").equalTo(true)
                .limitToFirst(1000)

        return busyTrucksQuery
    }
}