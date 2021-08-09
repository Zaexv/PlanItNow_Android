package com.planitnow.usecases.createplan

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.planitnow.R
import com.planitnow.usecases.base.BaseActivityRouter
import com.planitnow.usecases.viewplan.ViewPlanActivity

class CreatePlanRouter : BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, CreatePlanActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }


}