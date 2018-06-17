package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.google.firebase.database.*

import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Load

class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var loadNum: TextView = itemView.findViewById(R.id.load_number)
    var loadRate: TextView = itemView.findViewById(R.id.load_rate)
    var loadCustomer: TextView = itemView.findViewById(R.id.load_customer)
    var loadStart: TextView = itemView.findViewById(R.id.load_start)
    var loadDest: TextView = itemView.findViewById(R.id.load_dest)

    fun bindToLoad(load: Load) {
        loadNum.text = "#${load.loadId}"
        loadRate.text = "₴${load.customerRate}"
        loadCustomer.text = load.customer
        mFacilities.child(load.shipper).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                loadStart.text = p0!!.child("facilityName").getValue(String::class.java)
            }
        })
        mFacilities.child(load.consignee).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                loadDest.text = p0!!.child("facilityName").getValue(String::class.java)
            }
        })
    }

    companion object {
        private var mFacilities = FirebaseDatabase.getInstance().reference.child("facilities").child(getUid())
    }
}