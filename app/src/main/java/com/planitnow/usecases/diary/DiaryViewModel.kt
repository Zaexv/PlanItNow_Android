package com.planitnow.usecases.diary

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.AllPlansQuery
import com.planitnow.backend.ApolloQueryHandler

class DiaryViewModel : ViewModel() {

    var allPlans = listOf<AllPlansQuery.AllPlan>()

    suspend fun refreshPlans() {
        val res = try {
            ApolloQueryHandler.getAllPlans(isDiary=true)
        } catch (e: ApolloException) {
            return
        }
        val plans = res.data?.allPlans?.filterNotNull()
        if (plans != null && plans.isNotEmpty() && !res.hasErrors()) {
            allPlans = plans
        }
    }

}