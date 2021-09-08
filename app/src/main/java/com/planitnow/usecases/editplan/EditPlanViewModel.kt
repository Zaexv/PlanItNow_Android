package com.planitnow.usecases.editplan

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.DetailedPlanQuery
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.backend.ApolloQueryHandler
import com.planitnow.model.session.Session

class EditPlanViewModel : ViewModel() {
    lateinit var detailedPlan: DetailedPlanQuery.DetailedPlan

    suspend fun getPlanById(id: String): DetailedPlanQuery.DetailedPlan {
        val detailedPlanQuery = ApolloQueryHandler.getDetailedPlan(Integer.parseInt(id))

        if (detailedPlanQuery.hasErrors())
            throw Exception("Ha habido un error obteniendo el plan")

        detailedPlan = detailedPlanQuery.data?.detailedPlan!!

        return detailedPlan
    }

    suspend fun editPlan(
        context: Context,
        title: String,
        description: String,
        location: String,
        initHour: String,
        endHour: String,
        initDate: String,
        isPublic: Boolean,
        urlPlanPicture: String,
        maxParticipants: String,
    ): Boolean {

        var res = try {
            ApolloMutationHandler.editPlan(
                Integer.parseInt(detailedPlan.id),
                title,
                description,
                location,
                initHour,
                endHour,
                initDate,
                isPublic,
                urlPlanPicture,
                Integer.parseInt(maxParticipants),
            )
        } catch (e: ApolloException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            return false
        }

        if (res.hasErrors()) {
            Toast.makeText(context, res.errors!![0].message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context, context.getText(R.string.success_edit_profile),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }

        return false
    }


}