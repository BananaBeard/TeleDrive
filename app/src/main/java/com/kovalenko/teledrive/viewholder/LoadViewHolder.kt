package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Load

class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var loadNum: TextView
    var loadRate: TextView
    var loadCustomer: TextView
    var loadStart: TextView
    var loadDest: TextView

    init {
        loadNum = itemView.findViewById(R.id.load_number)
        loadRate = itemView.findViewById(R.id.load_rate)
        loadCustomer = itemView.findViewById(R.id.load_customer)
        loadStart = itemView.findViewById(R.id.load_start)
        loadDest = itemView.findViewById(R.id.load_dest)
    }

    fun bindToLoad(load: Load) {
        loadNum.text = load.loadId
        loadRate.text = load.customerRate.toString()
        loadCustomer.text = load.customer
        loadStart.text = load.shipper?.facilityName ?: "No data"
        loadDest.text = load.consignee?.facilityName ?: "No data"
    }
}