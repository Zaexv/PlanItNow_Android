package com.planitnow.backend

import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.graphql.models.AllPlansQuery
import com.planitnow.model.session.Session

object ApolloQueryHandler {

    private var SERVER_ADDRESS = "http://10.0.2.2:8000/graphql"
    private var token = Session.instance.getToken()
    private var apolloClient = ApolloClient(
        networkTransport = HttpNetworkTransport(
            serverUrl = SERVER_ADDRESS,
            interceptors = listOf(AuthorizationInterceptor(token))
            )
        )

    suspend fun getAllPlans(): ApolloResponse<AllPlansQuery.Data> {
        return apolloClient.query(AllPlansQuery())
    }



}