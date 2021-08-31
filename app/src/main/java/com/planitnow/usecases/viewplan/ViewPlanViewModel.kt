package com.planitnow.usecases.viewplan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.ApolloResponse
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.model.session.Session
import com.planitnow.usecases.base.BaseActivityRouter

class ViewPlanViewModel : ViewModel() {

    lateinit var detailedPlan: DetailedPlanQuery.DetailedPlan
    var userIsParticipating = false

    suspend fun getPlanById(id: String): DetailedPlanQuery.DetailedPlan {
        val detailedPlanQuery = ApolloQueryHandler.getDetailedPlan(Integer.parseInt(id))

        if (detailedPlanQuery.hasErrors())
            throw Exception("Ha habido un error obteniendo el plan") //TODO meter en Strings.xml

        detailedPlan = detailedPlanQuery.data?.detailedPlan!!

        val participantsList =  detailedPlan.participatingPlan.map { participatingPlan -> participatingPlan.participantUser.id }
        if(Session.instance.me.id in participantsList) userIsParticipating = true

        return detailedPlan
    }

    suspend fun deleteViewingPlan(): Boolean {
        val deletedPlan = ApolloMutationHandler.deletePlan(Integer.parseInt(detailedPlan.id))
        if (deletedPlan.hasErrors()) {
            println(deletedPlan.errors.toString())
            return false
        }
        return deletedPlan.data?.deletePlan?.ok!!
    }

    suspend fun participateInPlan(c: Context): Boolean {
        val participation =
            ApolloMutationHandler.participateInPlan(Integer.parseInt(detailedPlan.id))
        if (participation.hasErrors()) {
            Toast.makeText(c, participation.errors!![0].message, Toast.LENGTH_SHORT).show()
            return false
        }

        if (participation.data!!.participateInPlan!!.planParticipation == null) {
            Toast.makeText(c, "Te has borrado del plan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(c, "Te has apuntado al plan", Toast.LENGTH_SHORT).show()
        }

        getPlanById(detailedPlan.id)

        return true
    }


}