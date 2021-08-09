package com.planitnow.model.session

import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.backend.ApolloQueryHandler
import kotlinx.coroutines.coroutineScope

class Session {

    private var token: String = ""

    companion object {
        val instance = Session()
    }

    suspend fun doLogin(username: String, password: String) {

        val res = try {
            ApolloMutationHandler.tokenAuth(username, password)
        } catch ( e: ApolloException){
            return;
        }
        if (!res.hasErrors()) {
            token = res.data?.tokenAuth!!.token
        }
     }

    fun getToken():String{
        return token
    }

}