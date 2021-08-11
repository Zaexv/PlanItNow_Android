package com.planitnow.backend

import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.graphql.models.AllPlansQuery
import com.planitnow.model.session.Session

object ApolloQueryHandler {

    suspend fun getAllPlans(): ApolloResponse<AllPlansQuery.Data> {
        return ApolloClientSingleton.instance.apolloClient.query(AllPlansQuery())
    }



}