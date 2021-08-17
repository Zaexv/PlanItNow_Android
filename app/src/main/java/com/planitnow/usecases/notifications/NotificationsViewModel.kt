package com.planitnow.usecases.notifications

import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.ReceivedFriendRequestsQuery
import com.graphql.models.SendFriendRequestMutation
import com.graphql.models.adapter.ReceivedFriendRequestsQuery_ResponseAdapter
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.backend.ApolloQueryHandler

class NotificationsViewModel : ViewModel() {
    var receivedFriendRequests = listOf<ReceivedFriendRequestsQuery.ReceivedFriendRequest>()
    val notifications = mutableListOf<Notification>()
    
    suspend fun refreshReceivedFriendRequests() {
        val res = try {
            ApolloQueryHandler.getReceivedFriendRequests()

        } catch (e: ApolloException) {
            return
        }
        val queryFriendRequests = res.data?.receivedFriendRequests?.filterNotNull()
        if (queryFriendRequests != null && queryFriendRequests.isNotEmpty() && !res.hasErrors()) {
            this.receivedFriendRequests = queryFriendRequests
        }
    }
    
    suspend fun acceptFriendRequest(frId : Int): Boolean{
        val res = try {
            ApolloMutationHandler.acceptFriendRequest(frId)
        } catch (e: ApolloException){
            return false
        }
        if(!res.hasErrors()) return true
        return false
    }
    suspend fun rejectFriendRequest(frId : Int): Boolean{
        val res = try {
            ApolloMutationHandler.rejectFriendRequest(frId)
        } catch (e: ApolloException){
            println(e.message)
            return false
        }
        if(!res.hasErrors()) return true
        println(res.errors.toString())
        return false
    }

}