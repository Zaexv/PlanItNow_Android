package com.planitnow.usecases.mainactivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.planitnow.R
import com.planitnow.usecases.base.BaseActivityRouter
import com.planitnow.usecases.createplan.CreatePlanActivity

class MainActivityRouter : BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, MainActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }


}