package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Load

class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var loadNum: TextView = itemView.findViewById(R.id.load_number)
    var loadRate: TextView = itemView.findViewById(R.id.load_rate)
    var loadCustomer: TextView = itemView.findViewById(R.id.load_customer)
    var loadStart: TextView = itemView.findViewById(R.id.load_start)
    var loadDest: TextView = itemView.findViewById(R.id.load_dest)

    fun bindToLoad(load: Load) {
        loadNum.text = load.loadId
        loadRate.text = load.customerRate.toString()
        loadCustomer.text = load.customer
        loadStart.text = load.shipper
        loadDest.text = load.consignee
    }
}