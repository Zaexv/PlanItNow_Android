package com.planitnow.usecases.diary

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.planitnow.R
import com.planitnow.usecases.base.BaseActivityRouter
import com.planitnow.usecases.register.RegisterActivity

class DiaryRouter : BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, DiaryActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(
            R.anim.activity_back_in,
            R.anim.activity_back_out
        )
    }

}