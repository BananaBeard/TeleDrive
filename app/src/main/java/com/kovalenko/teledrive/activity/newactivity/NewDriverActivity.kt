package com.kovalenko.teledrive.activity.newactivity

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.R
import com.kovalenko.teledrive.activity.getUid
import com.kovalenko.teledrive.models.Driver
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_new_driver.*
import java.util.*

class NewDriverActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    var dateAndTime = Calendar.getInstance()
    var dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        dateAndTime.set(Calendar.YEAR, year)
        dateAndTime.set(Calendar.MONTH, month)
        dateAndTime.set(Calendar.DAY_OF_MONTH, day)
        setInitialDate()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_driver)

        new_driver_layout.background.alpha = 50

        mDatabase = FirebaseDatabase.getInstance().reference

        fab_submit_driver.setOnClickListener {
            submitNewDriver()
        }

        button_pick_driver_date.setOnClickListener {
            setDate()
        }
    }

    private fun submitNewDriver() {
        if (!validateNewDriver()) {
            return
        }

        var userId = getUid()

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "getUser:onCancelled", p0!!.toException())
            }

            override fun onDataChange(p0: DataSnapshot?) {
                var user = p0!!.getValue(User::class.java)

                if (user == null) {
                    Log.e(TAG, "User $userId is unexpectedly null")
                    Toast.makeText(this@NewDriverActivity,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show()
                } else {
                    addNewDriver(
                            userId,
                            input_first_name.text.toString().trim(),
                            input_second_name.text.toString().trim(),
                            input_driver_date.text.toString().trim()
                    )
                    finish()
                }
            }
        })
    }

    private fun validateNewDriver() : Boolean {
        var result = true

        if (input_first_name.text.isNullOrEmpty()) {
            input_first_name.error = "Required"
            result = false
        }
        if (input_second_name.text.isNullOrEmpty()) {
            input_second_name.error = "Required"
            result = false
        }
        if (input_driver_date.text.isNullOrEmpty()) {
            input_driver_date.error = "Required"
            result = false
        }

        return result
    }

    private fun addNewDriver(userdId: String, firstName: String, secondName: String, birthDate: String) {
        var key = mDatabase.child("drivers").child(userdId).push().key
        var driver = Driver(firstName, secondName, birthDate)
        mDatabase.child("drivers").child(userdId).child(key).setValue(driver)
    }

    private fun setDate() {
        DatePickerDialog(
                this@NewDriverActivity,
                dateListener,dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun setInitialDate() {
        input_driver_date.setText(DateUtils.formatDateTime(
                this,
                dateAndTime.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE
        ))
    }

    companion object {
        private val TAG = "NewDriverActivity"
    }
}
