package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.planitnow.model.session.Session

class ApolloClientSingleton {

    companion object {
        val instance = ApolloClientSingleton()
    }

    private var SERVER_ADDRESS = "http://209.38.225.193/graphql"
    // private var SERVER_ADDRESS = "https://planitnowalpha.herokuapp.com/graphql"

    private var token = Session.instance.getToken()

    var apolloClient =
        ApolloClient(
            networkTransport = HttpNetworkTransport(
                serverUrl = SERVER_ADDRESS,
                interceptors = listOf(AuthorizationInterceptor(token))
            )
        )
}