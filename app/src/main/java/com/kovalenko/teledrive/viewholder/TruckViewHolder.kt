package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Truck

class TruckViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var truckBrand: TextView = itemView.findViewById(R.id.truck_brand)
    var truckModel: TextView = itemView.findViewById(R.id.truck_model)
    var truckTractorYear: TextView = itemView.findViewById(R.id.tractor_year)
    var truckReeferYear: TextView = itemView.findViewById(R.id.reefer_year)

    fun bindToTruck(truck: Truck) {
        truckBrand.text = truck.brand
        truckModel.text = truck.model
        truckReeferYear.text = truck.reeferYear.toString()
        truckTractorYear.text = truck.tractorYear.toString()
    }
}