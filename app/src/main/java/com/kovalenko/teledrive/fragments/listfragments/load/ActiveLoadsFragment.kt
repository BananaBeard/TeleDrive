package com.kovalenko.teledrive.fragments.listfragments.load

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.kovalenko.teledrive.activity.getUid

class ActiveLoadsFragment: LoadListFragment() {
    override fun getQuery(databaseReference: DatabaseReference): Query {
        var activeLoadsQuery = databaseReference.child("loads").child(getUid()).orderByChild("status").equalTo("Active")
                .limitToFirst(1000)

        return activeLoadsQuery
    }

}