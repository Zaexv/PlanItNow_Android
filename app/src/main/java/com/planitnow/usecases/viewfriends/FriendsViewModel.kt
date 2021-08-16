package com.planitnow.usecases.viewfriends

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.graphql.models.MyFriendsQuery
import com.planitnow.backend.ApolloQueryHandler

class FriendsViewModel : ViewModel() {

    var myFriends = listOf<MyFriendsQuery.MyFriend>()

    suspend fun refreshMyFriends(){
        val res = try{
            ApolloQueryHandler.getMyFriends()
        } catch (e :ApolloException){
            return
        }
        val friends = res.data?.myFriends?.filterNotNull()
        if(friends != null && friends.isNotEmpty() && !res.hasErrors()){
            myFriends = friends
        }
    }

}