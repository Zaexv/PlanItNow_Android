package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.graphql.models.AllPlansQuery

object ApolloQueryHandler {
    var SERVER_ADDRESS = "http://10.0.2.2:8000/graphql"
    var apolloClient = ApolloClient(serverUrl = SERVER_ADDRESS)

    suspend fun getAllPlans(): ApolloResponse<AllPlansQuery.Data> {
        return apolloClient.query(AllPlansQuery())
    }

}