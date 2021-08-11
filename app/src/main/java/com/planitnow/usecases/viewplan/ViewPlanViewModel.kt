package com.planitnow.usecases.viewplan

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.ApolloResponse
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.usecases.base.BaseActivityRouter

class ViewPlanViewModel  : ViewModel() {

    lateinit var detailedPlan : DetailedPlanQuery.DetailedPlan

    suspend fun getPlanById(id : String): DetailedPlanQuery.DetailedPlan {
        val detailedPlanQuery = ApolloQueryHandler.getDetailedPlan(Integer.parseInt(id))

        if(detailedPlanQuery.hasErrors())
            throw Exception("Ha habido un error obteniendo el plan") //TODO meter en Strings.xml

        detailedPlan = detailedPlanQuery.data?.detailedPlan!!

        return detailedPlan
    }
}