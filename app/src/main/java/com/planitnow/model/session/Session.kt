package com.planitnow.model.session

import android.content.Context
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.MeQuery
import com.planitnow.backend.ApolloClientSingleton
import com.planitnow.backend.ApolloLoginHandler
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.backend.ApolloQueryHandler
import kotlinx.coroutines.coroutineScope

class Session {

    private var token: String = ""
    lateinit var me: MeQuery.Me

    companion object {
        val instance = Session()
    }

    suspend fun doLogin(username: String, password: String): Boolean {
        try {
            val tokenRes = ApolloLoginHandler.tokenAuth(username, password)
            if (tokenRes.hasErrors()) {
                println("GraphQL Error: " + tokenRes.errors.toString())
                return false
            }
            instance.token = tokenRes.data?.tokenAuth!!.token
            ApolloClientSingleton.instance
            me = ApolloQueryHandler.getLoggedUser().data?.me!!
        } catch (e: ApolloException) {
            println("Apollo Error: " + e.message)
            return false;
        }
        return true
    }

    suspend fun verifyToken(token: String): Boolean {
        var verified = false
        val res = try {
            ApolloLoginHandler.verifyToken(token)
        } catch (e: ApolloException) {
            println(e.message)
            return verified
        }

        if (!res.hasErrors()) {
            verified = true
            instance.token = token
            ApolloClientSingleton.instance
            me = ApolloQueryHandler.getLoggedUser().data?.me!!
        }
        return verified
    }


    fun getToken(): String {
        println("Mi Token es:" + instance.token)
        return instance.token
    }


}