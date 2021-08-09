package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.graphql.models.AllPlansQuery
import com.graphql.models.TokenAuthMutation

object ApolloMutationHandler {

    private var SERVER_ADDRESS = "http://10.0.2.2:8000/graphql"
    private var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNjI4NTIzOTI0LCJvcmlnSWF0IjoxNjI4NTIzNjI0fQ.4gv7ok4cS5gxSJm2tYJm8hwik0I6_3ta1pubCBKvP4g"
    private var apolloClient = ApolloClient(
        networkTransport = HttpNetworkTransport(
            serverUrl = SERVER_ADDRESS,
            interceptors = listOf(AuthorizationInterceptor(token))
        )
    )

    suspend fun tokenAuth(username:String, password:String): ApolloResponse<TokenAuthMutation.Data> {
        return apolloClient.mutate(TokenAuthMutation(username,password))
    }

}