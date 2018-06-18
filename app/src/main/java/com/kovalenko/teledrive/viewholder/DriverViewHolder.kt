package com.kovalenko.teledrive.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Driver
import kotlinx.android.synthetic.main.item_driver.view.*

class DriverViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var firstName: TextView = itemView.findViewById(R.id.first_name)
    private var secondName: TextView = itemView.findViewById(R.id.second_name)
    private var birthDate: TextView = itemView.findViewById(R.id.birth_date)

    fun bindToDriver(driver: Driver) {

        firstName.text = driver.firstName
        secondName.text = driver.secondName
        birthDate.text = driver.birthDate
    }
}