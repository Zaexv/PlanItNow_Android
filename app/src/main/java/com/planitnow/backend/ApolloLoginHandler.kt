package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.graphql.models.TokenAuthMutation

object ApolloLoginHandler {

    private var SERVER_ADDRESS = "http://10.0.2.2:8000/graphql"
    private var apolloClient = ApolloClient(SERVER_ADDRESS)

    suspend fun tokenAuth(username:String, password:String): ApolloResponse<TokenAuthMutation.Data> {
        return ApolloLoginHandler.apolloClient.mutate(TokenAuthMutation(username,password))
    }

}