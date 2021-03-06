package com.kovalenko.teledrive.fragments.listfragments.driver

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class AvailableDriversFragment: DriverListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var availableDriversQuery = databaseReference.child("drivers").child(getUid()).orderByChild("working").equalTo(false)
                .limitToFirst(1000)

        return availableDriversQuery
    }
}