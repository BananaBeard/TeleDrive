package com.kovalenko.teledrive.fragments.listfragments.load

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class AvailableLoadsFragment: LoadListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var avalLoadsQuery = databaseReference.child("loads").child(getUid()).orderByChild("status").equalTo("Available")
                .limitToFirst(1000)

        return avalLoadsQuery
    }
}