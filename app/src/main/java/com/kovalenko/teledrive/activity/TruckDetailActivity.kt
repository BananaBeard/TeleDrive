package com.kovalenko.teledrive.activity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Truck
import kotlinx.android.synthetic.main.activity_truck_detail.*

class TruckDetailActivity : DetailActivity() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mTruckReference: DatabaseReference
    private lateinit var mTruckListener: ValueEventListener
    private lateinit var mTruckKey: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_detail)

        truck_detail_layout.background.alpha = 50

        edit_truck_brand.inputType = InputType.TYPE_NULL
        edit_truck_model.inputType = InputType.TYPE_NULL
        edit_tractor_year.inputType = InputType.TYPE_NULL
        edit_reefer_year.inputType = InputType.TYPE_NULL

        mTruckKey = intent.getStringExtra(EXTRA_TRUCK_KEY)

        mDatabase = FirebaseDatabase.getInstance().reference

        mTruckReference = FirebaseDatabase.getInstance().reference
                .child("trucks").child(mTruckKey)

        with(switch_edit_truck){
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
        }

        with(switch_truck_busy){
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
        }

        switch_edit_truck.setOnCheckedChangeListener { _, p1 ->
            if (p1) {
                animateSwitch(switch_edit_truck)
                enableEdit(p1)
            } else {
                animateSwitch(switch_edit_truck)
                enableEdit(p1)
            }
        }

        switch_truck_busy.setOnCheckedChangeListener { _, p1 ->
            if (p1) {
                animateSwitch(switch_truck_busy)
            } else {
                animateSwitch(switch_truck_busy)
            }
        }

        fab_edit_truck.setOnClickListener {
            submitChanges()
        }
    }

    override fun onStart() {
        super.onStart()

        var truckListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {

                var truck = dataSnapshot!!.getValue(Truck::class.java)

                edit_truck_brand.setText(truck!!.brand)
                edit_truck_model.setText(truck.model)
                edit_tractor_year.setText(truck.tractorYear.toString())
                edit_reefer_year.setText(truck.reeferYear.toString())
                switch_truck_busy.isChecked = truck.isUsed
            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "loadTruck:onCancelled", p0!!.toException())

                Toast.makeText(this@TruckDetailActivity, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
            }
        }

        mTruckReference.addValueEventListener(truckListener)

        mTruckListener = truckListener
    }

    override fun onStop() {
        super.onStop()

        if (mTruckListener != null) {
            mTruckReference.removeEventListener(mTruckListener)
        }
    }

    override fun enableEdit(switch: Boolean) {
        if (switch) {
            edit_truck_brand.inputType = InputType.TYPE_CLASS_TEXT
            edit_truck_model.inputType = InputType.TYPE_CLASS_TEXT
            edit_tractor_year.inputType = InputType.TYPE_CLASS_NUMBER
            edit_reefer_year.inputType = InputType.TYPE_CLASS_NUMBER
            switch_truck_busy.isEnabled = true
            fab_edit_truck.visibility = View.VISIBLE
        } else {
            edit_truck_brand.inputType = InputType.TYPE_NULL
            edit_truck_model.inputType = InputType.TYPE_NULL
            edit_tractor_year.inputType = InputType.TYPE_NULL
            edit_reefer_year.inputType = InputType.TYPE_NULL
            switch_truck_busy.isEnabled = false
            fab_edit_truck.visibility = View.INVISIBLE
        }
    }

    private fun animateSwitch(view: View) {
        var anim = ObjectAnimator.ofInt(view.background, "alpha", 80)
        anim.duration = ANIM_DURATION

        var anim2 = ObjectAnimator.ofInt(view.background, "alpha", 0)
        anim2.duration = ANIM_DURATION
        anim2.startDelay = ANIM_DURATION

        anim.start()
        anim2.start()
    }

    override fun submitChanges() {

        if (validateChanges()) {
            val updateMap = HashMap<String, Any>()
            updateMap["brand"] = edit_truck_brand.text.toString()
            updateMap["model"] = edit_truck_model.text.toString()
            updateMap["tractorYear"] = edit_tractor_year.text.toString().toInt()
            updateMap["reeferYear"] = edit_reefer_year.text.toString().toInt()
            updateMap["used"] = switch_truck_busy.isChecked
            mDatabase.child("trucks").child(mTruckKey).updateChildren(updateMap)
            finish()
        } else {
            return
        }
    }

    override fun validateChanges(): Boolean {

        var result = true

        if (edit_truck_brand.text.isEmpty()) {
            edit_truck_brand.error = "Required"
            result = false
        }

        if (edit_truck_model.text.isEmpty()) {
            edit_truck_model.error = "Required"
            result = false
        }

        if (edit_tractor_year.text.isEmpty()) {
            edit_tractor_year.error = "Required"
            result = false
        }

        if (edit_reefer_year.text.isEmpty()) {
            edit_reefer_year.error = "Required"
            result = false
        }
        return result
    }

    companion object {
        private val TAG = "TruckDetailActivity"
        const val EXTRA_TRUCK_KEY = "truck_key"
    }
}
