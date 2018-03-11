package com.kovalenko.teledrive

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

fun AppCompatActivity.getUid() = FirebaseAuth.getInstance().currentUser!!.uid

inline fun <reified T : Activity> Context.getIntent() = Intent(this, T::class.java)