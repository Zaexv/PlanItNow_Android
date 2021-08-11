package com.planitnow.model.session

import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.backend.ApolloClientSingleton
import com.planitnow.backend.ApolloLoginHandler
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.backend.ApolloQueryHandler
import kotlinx.coroutines.coroutineScope

class Session {

    private var token: String = ""

    companion object {
        val instance = Session()
    }

    suspend fun doLogin(username: String, password: String) : Boolean {
        val res = try {
            ApolloLoginHandler.tokenAuth(username, password)
        } catch (e: ApolloException){
            return false;
        }

        return if (!res.hasErrors()) {
            instance.token = res.data?.tokenAuth!!.token
            ApolloClientSingleton.instance
            true;
        } else {
            false
        }
     }

    fun getToken():String{
        println("Mi Token es:" + instance.token)
        return instance.token
    }

}