package com.kovalenko.teledrive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kovalenko.teledrive.models.User
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        button_sign_in.setOnClickListener(this)
        link_signup.setOnClickListener(this)
        progressbar.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser != null) {
            onAuthSuccess(mAuth.currentUser!!)
        }
    }

    private fun signIn() {
        Log.d(TAG, "signIn")

        if (!validateForm()) {
            return
        }

        progressbar.visibility = View.VISIBLE

        var email = input_email.text.toString()
        var password = input_password.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    Log.d(TAG, "signIn:onComplete" + task.isSuccessful)
                    progressbar.visibility = View.INVISIBLE

                    if (task.isSuccessful) {
                        onAuthSuccess(task.result.user)
                    } else {
                        Toast.makeText(this@SignInActivity, "Sign in Failed",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    private fun signUp() {
        Log.d(TAG, "signUp")

        if (!validateForm()) {
            return
        }

        progressbar.visibility = View.VISIBLE

        var email = input_email.text.toString()
        var password = input_password.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    Log.d(TAG, "createUser:onComplete" + task.isSuccessful)
                    progressbar.visibility = View.INVISIBLE

                    if (task.isSuccessful) {
                        onAuthSuccess(task.result.user)
                    } else {
                        Toast.makeText(this@SignInActivity, "Sign Up Failed",
                                Toast.LENGTH_SHORT).show()
                    }
        }

    }

    private fun onAuthSuccess(user: FirebaseUser) {
        var username = usernameFromEmail(user.email!!)

        writeNewUser(user.uid, username, user.email!!)

        startActivity(getIntent<MainActivity>())
        finish()
    }

    private fun validateForm(): Boolean {
        var result = true

        if (input_email.text.toString().isEmpty() || !input_email.text.toString().contains("@")) {
            result = false
            input_email.error = "Required"
        } else {
            input_email.error = null
        }

        if (input_password.text.toString().isEmpty()) {
            result = false
            input_email.error = "Required"
        } else {
            input_password.error = null
        }

        return result
    }

    private fun usernameFromEmail(email: String) = email.split("@")[0]

    private fun writeNewUser(userId: String, name: String, email: String) {
        var user = User(name, email)

        mDatabase.child("users").child(userId).setValue(user)
    }

    override fun onClick(p0: View?) {
        var i = p0!!.id

        when(i) {
            R.id.button_sign_in -> signIn()
            R.id.link_signup -> signUp()
        }
    }

    companion object {
        private val TAG = "SignInActivity"
    }
}
