package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.graphql.models.CreateUserMutation
import com.graphql.models.TokenAuthMutation
import com.graphql.models.VerifyTokenMutation

object ApolloLoginHandler {


    private var SERVER_ADDRESS = "http://209.38.225.193/graphql"
    //  private var SERVER_ADDRESS = "https://planitnowalpha.herokuapp.com/graphql"
    private var apolloClient = ApolloClient(SERVER_ADDRESS)

    suspend fun tokenAuth(
        username: String,
        password: String
    ): ApolloResponse<TokenAuthMutation.Data> {
        return ApolloLoginHandler.apolloClient.mutate(TokenAuthMutation(username, password))
    }

    suspend fun verifyToken(
        token:String
    ): ApolloResponse<VerifyTokenMutation.Data> {
        return ApolloLoginHandler.apolloClient.mutate(VerifyTokenMutation(token))
    }

    suspend fun register(
        password: String,
        username: String,
        birthDate: String,
        email: String,
        firstName: String,
        lastName: String
    ): ApolloResponse<CreateUserMutation.Data> {
        return ApolloLoginHandler.apolloClient.mutate(
            CreateUserMutation(password, username, birthDate, email, firstName, lastName)
        )
    }
}