package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.graphql.models.AllPlansQuery
import com.graphql.models.CreatePlanMutation
import com.graphql.models.TokenAuthMutation
import com.planitnow.model.session.Session

object ApolloMutationHandler {

    suspend fun createPlan(
        title:String,
        description:String,
        location:String,
        initHour:String,
        endHour:String,
        initDate:String,
        isPublic: Boolean
    ): ApolloResponse<CreatePlanMutation.Data> {
        return ApolloClientSingleton.instance.apolloClient.mutate(CreatePlanMutation(
            description = description,
            title = title,
            location = location,
            initHour = initHour,
            endHour = endHour,
            initDate = initDate,
            isPublic = Optional.Present(isPublic)
        ))
    }
}