package com.kovalenko.teledrive.activity.detailactivity

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.format.DateUtils
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Driver
import kotlinx.android.synthetic.main.activity_driver_detail.*
import java.util.*

class DriverDetailActivity : DetailActivity() {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mDriverReference: DatabaseReference
    private lateinit var mDriverListener: ValueEventListener
    private lateinit var mDriverKey: String

    var dateAndTime = Calendar.getInstance()
    var dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        dateAndTime.set(Calendar.YEAR, year)
        dateAndTime.set(Calendar.MONTH, month)
        dateAndTime.set(Calendar.DAY_OF_MONTH, day)
        setInitialDate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_detail)

        driver_detail_layout.background.alpha = 50

        edit_first_name.inputType = InputType.TYPE_NULL
        edit_second_name.inputType = InputType.TYPE_NULL
        edit_driver_date.inputType = InputType.TYPE_NULL

        mDriverKey = intent.getStringExtra(EXTRA_DRIVER_KEY)
        mDatabase = FirebaseDatabase.getInstance().reference
        mDriverReference = mDatabase.child("drivers").child(getUid()).child(mDriverKey)

        with(switch_edit_driver) {
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
            setBackgroundColor(Color.GRAY)
            background.alpha = 0
        }

        switch_edit_driver.setOnCheckedChangeListener { _, b ->
            animateSwitch(switch_edit_driver)
            enableEdit(b)
        }

        fab_edit_driver.setOnClickListener {
            submitChanges()
        }
    }

    override fun onStart() {
        super.onStart()

        var driversListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                var driver = p0!!.getValue(Driver::class.java)

                edit_first_name.setText(driver!!.firstName)
                edit_second_name.setText(driver!!.secondName)
                edit_driver_date.setText(driver!!.birthDate)
            }
        }

        mDriverReference.addValueEventListener(driversListener)
        mDriverListener = driversListener
    }

    override fun onStop() {
        super.onStop()

        mDriverReference.removeEventListener(mDriverListener)
    }

    override fun submitChanges() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enableEdit(switch: Boolean) {
        if (switch) {
            edit_first_name.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            edit_second_name.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            edit_driver_date.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            button_edit_pick_driver_date.isEnabled = true
        } else {
            edit_first_name.inputType = InputType.TYPE_NULL
            edit_second_name.inputType = InputType.TYPE_NULL
            edit_driver_date.inputType = InputType.TYPE_NULL
            button_edit_pick_driver_date.isEnabled = false
        }
    }

    override fun validateChanges(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun setDate() {
        DatePickerDialog(
                this@DriverDetailActivity,
                dateListener,dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setInitialDate() {
        edit_driver_date.setText(DateUtils.formatDateTime(
                this,
                dateAndTime.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE
        ))
    }

    companion object {
        private val TAG = "DriverDetailActivity"
        const val EXTRA_DRIVER_KEY = "driver_key"
    }
}
