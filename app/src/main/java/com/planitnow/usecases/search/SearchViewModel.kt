package com.planitnow.usecases.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.AllPlansQuery
import com.graphql.models.RecommendOrSearchQuery
import com.planitnow.backend.ApolloQueryHandler

class SearchViewModel : ViewModel() {

    var foundPlans = listOf<RecommendOrSearchQuery.RecommendedOrSearch>()

    suspend fun searchOrRecommendPlans(searchString: String) {
        val res = try {
            ApolloQueryHandler.getSearchRecommendPlans(searchString)
        } catch (e: ApolloException) {
            return
        }
        val plans = res.data?.recommendedOrSearch?.filterNotNull()
        if (plans != null && !res.hasErrors()) {
            foundPlans = plans
        }
    }
}
