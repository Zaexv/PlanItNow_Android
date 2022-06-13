package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.planitnow.model.session.Session

class ApolloClientSingleton {

    companion object {
        val instance = ApolloClientSingleton()
    }


    // Localhost IP in Android is 10.0.2.2:8000
    // Requires Python Backend Running Locally
    private var SERVER_ADDRESS = "http://10.0.2.2:8000/graphql"
    private var token = Session.instance.getToken()

    var apolloClient =
        ApolloClient(
            networkTransport = HttpNetworkTransport(
                serverUrl = SERVER_ADDRESS,
                interceptors = listOf(AuthorizationInterceptor(token))
            )
        )
}