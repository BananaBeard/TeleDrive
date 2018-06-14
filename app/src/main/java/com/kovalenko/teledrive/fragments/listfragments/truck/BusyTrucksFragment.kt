package com.kovalenko.teledrive.fragments.listfragments.truck

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class BusyTrucksFragment : TruckListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var busyTrucksQuery = databaseReference.child("trucks").child(getUid()).orderByChild("used").equalTo(true)
                .limitToFirst(1000)

        return busyTrucksQuery
    }
}