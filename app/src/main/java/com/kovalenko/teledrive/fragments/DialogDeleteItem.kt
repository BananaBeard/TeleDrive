package com.kovalenko.teledrive.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

class DialogDeleteItem: DialogFragment(){

    lateinit var onAcceptListener: () -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Are you sure you want to delete this entry?")

        builder.setPositiveButton("Yes") { _, _ ->
            onAcceptListener.invoke()
            dismiss()
        }
        builder.setNegativeButton("No") { _, _ -> dismiss() }
        return builder.create()
    }
}