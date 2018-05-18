package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Facility

class FacilityViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var name: TextView = itemView.findViewById(R.id.facility_name)
    var address: TextView = itemView.findViewById(R.id.facility_address)

    fun bindToFacility(facility: Facility) {
        name.text = facility.facilityName
        address.text = facility.address
    }
}