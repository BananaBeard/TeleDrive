package com.kovalenko.teledrive.fragments.listfragments.driver

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class AllDriversFragment: DriverListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var activeLoadsQuery = databaseReference.child("drivers").child(getUid())
                .limitToFirst(1000)

        return activeLoadsQuery
    }
}