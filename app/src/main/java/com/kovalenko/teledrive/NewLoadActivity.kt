package com.kovalenko.teledrive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import com.kovalenko.teledrive.models.Load
import com.kovalenko.teledrive.models.LoadType
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_new_load.*

class NewLoadActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_load)

        mDatabase = FirebaseDatabase.getInstance().reference
        fab_submit_load.setOnClickListener {
            submitLoad()
            field_load_cust.setText("sasassa")
        }
    }

    private fun submitLoad() {

        val loadId = field_load_id.text.toString()
        val customer = field_load_cust.text.toString()

        val custRate = field_cust_rate.text.toString().toDouble()
        val driverRate = field_driv_rate.text.toString().toDouble()

        val userId = getUid()

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot?) {
                var user = p0!!.getValue(User::class.java)

                if (user == null) {
                    Log.e(TAG, "User $userId is unexpectedly null")
                    Toast.makeText(this@NewLoadActivity,
                            "Error: could not fetch user.",
                            Toast.LENGTH_SHORT).show()
                } else {
                    addNewLoad(userId, loadId, customer, custRate, driverRate)
                }
                finish()
            }

            override fun onCancelled(p0: DatabaseError?) {
                Log.w(TAG, "getUser:onCancelled", p0!!.toException())
            }
        })
    }

    fun addNewLoad(
            userId: String,
            loadId: String,
            customer: String,
            custRate: Double,
            driverRate: Double
    ) {
        var key = mDatabase.child("loads").push().key
        var load = Load()

        mDatabase.child("loads").child(key).setValue(load)
//        var loadValues = load.toMap()
//        var childUpdates = HashMap<String, Any?>()
//        childUpdates["/loads/" + key] = loadValues
//
//        mDatabase.updateChildren(childUpdates)
    }

    companion object {
        private val TAG = "NewLoadActivity"
    }
}
