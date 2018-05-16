package com.kovalenko.teledrive.activity

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.models.Truck
import kotlinx.android.synthetic.main.activity_truck_detail.*

class TruckDetailActivity : DetailActivity() {

    private lateinit var mTruckReference: DatabaseReference
    private lateinit var mTruckListener: ValueEventListener
    private lateinit var mTruckKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truck_detail)

        truck_detail_layout.background.alpha = 50

        mTruckKey = intent.getStringExtra(EXTRA_TRUCK_KEY)

        mTruckReference = FirebaseDatabase.getInstance().reference
                .child("trucks").child(mTruckKey)

        switchEditTruck.setBackgroundColor(Color.GRAY)
        switchEditTruck.background.alpha = 0
        switchTruckBusy.setBackgroundColor(Color.GRAY)
        switchTruckBusy.background.alpha = 0

        switchEditTruck.setOnCheckedChangeListener { _, p1 ->
            if (p1) {
                animateSwitch(switchEditTruck)
                buttonSaveTruck.visibility = View.VISIBLE
            } else {
                animateSwitch(switchEditTruck)
                buttonSaveTruck.visibility = View.INVISIBLE
            }
        }

        switchTruckBusy.setOnCheckedChangeListener { _, p1 ->
            if (p1) {
                animateSwitch(switchTruckBusy)
            } else {
                animateSwitch(switchTruckBusy)
            }
        }

        buttonSaveTruck.setOnClickListener {
            submitChanges()
        }
    }

    private fun animateSwitch(view: View) {
        var anim = ObjectAnimator.ofInt(view.background, "alpha", 80)
        anim.duration = 500

        var anim2 = ObjectAnimator.ofInt(view.background, "alpha", 0)
        anim2.duration = 500
        anim2.startDelay = 500

        anim.start()
        anim2.start()
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
                switchTruckBusy.isChecked = truck.isUsed
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

    override fun submitChanges() {

    }

    companion object {
        private val TAG = "TruckDetailActivity"
        const val EXTRA_TRUCK_KEY = "truck_key"
    }
}
