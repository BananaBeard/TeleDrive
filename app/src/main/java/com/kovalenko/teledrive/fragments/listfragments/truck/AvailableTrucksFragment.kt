package com.kovalenko.teledrive.fragments.listfragments.truck

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class AvailableTrucksFragment : TruckListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var avalTrucksQuery = databaseReference.child("trucks").child(getUid()).orderByChild("used").equalTo(false)
                .limitToFirst(1000)

        return avalTrucksQuery
    }
}