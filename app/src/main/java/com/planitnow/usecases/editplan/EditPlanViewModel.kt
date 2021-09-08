package com.planitnow.usecases.editplan

import androidx.lifecycle.ViewModel
import com.graphql.models.DetailedPlanQuery
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.model.session.Session

class EditPlanViewModel  : ViewModel() {
    lateinit var detailedPlan: DetailedPlanQuery.DetailedPlan

    suspend fun getPlanById(id: String): DetailedPlanQuery.DetailedPlan {
        val detailedPlanQuery = ApolloQueryHandler.getDetailedPlan(Integer.parseInt(id))

        if (detailedPlanQuery.hasErrors())
            throw Exception("Ha habido un error obteniendo el plan")

        detailedPlan = detailedPlanQuery.data?.detailedPlan!!

        return detailedPlan
    }


}