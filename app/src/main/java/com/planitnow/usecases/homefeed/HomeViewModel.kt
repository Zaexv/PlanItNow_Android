package com.planitnow.usecases.homefeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.AllPlansQuery
import com.planitnow.backend.ApolloQueryHandler

class HomeViewModel : ViewModel() {

    var allPlans = listOf<AllPlansQuery.AllPlan>()

    suspend fun refreshPlans(){
        val res = try {
            ApolloQueryHandler.getAllPlans()
        } catch ( e: ApolloException){
            return
        }
        val plans = res.data?.allPlans?.filterNotNull()
        if (plans != null && plans.isNotEmpty() && !res.hasErrors()) {
            allPlans = plans
        }
    }
    }

