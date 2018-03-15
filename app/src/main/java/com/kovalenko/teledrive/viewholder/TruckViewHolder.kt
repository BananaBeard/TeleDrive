package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Truck
import kotlinx.android.synthetic.main.item_truck.*

class TruckViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    var truckBrand: TextView
    var truckModel: TextView
    var truckTraktorYear: TextView
    var truckReeferYear: TextView

    init {
        truckBrand = itemView.findViewById(R.id.truck_brand)
        truckModel = itemView.findViewById(R.id.truck_model)
        truckTraktorYear = itemView.findViewById(R.id.traktor_year)
        truckReeferYear = itemView.findViewById(R.id.reefer_year)
    }

    fun bindToTruck(truck: Truck) {
        truckBrand.text = truck.brand
        truckModel.text = truck.model
        truckReeferYear.text = truck.reeferYear.toString()
        truckTraktorYear.text = truck.tractorYear.toString()
    }
}