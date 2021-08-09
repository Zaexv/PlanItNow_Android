package com.planitnow.usecases.viewplan

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.planitnow.R
import com.planitnow.usecases.base.BaseActivityRouter

class ViewPlanRouter: BaseActivityRouter {

    override fun intent(activity: Context): Intent = Intent(activity, ViewPlanActivity::class.java)

    override fun launch(activity: Context) {
        activity.startActivity(intent(activity))
        (activity as Activity).overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
    }

}