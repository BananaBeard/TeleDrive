package com.kovalenko.teledrive.activity.detailactivity

import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

abstract class DetailActivity : AppCompatActivity() {

    private val ANIM_DURATION = 750L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun animateSwitch(view: View) {
        var anim = ObjectAnimator.ofInt(view.background, "alpha", 80)
        anim.duration = ANIM_DURATION

        var anim2 = ObjectAnimator.ofInt(view.background, "alpha", 0)
        anim2.duration = ANIM_DURATION
        anim2.startDelay = ANIM_DURATION

        anim.start()
        anim2.start()
    }

    abstract fun submitChanges()

    abstract fun enableEdit(switch: Boolean)

    abstract fun validateChanges(): Boolean
}
