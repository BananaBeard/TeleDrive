package com.kovalenko.teledrive.fragments.listfragments.driver

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class BusyDriversFragment: DriverListFragment(){
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var busyDriversQuery = databaseReference.child("drivers").child(getUid()).orderByChild("working").equalTo(true)
                .limitToFirst(1000)

        return busyDriversQuery
    }
}