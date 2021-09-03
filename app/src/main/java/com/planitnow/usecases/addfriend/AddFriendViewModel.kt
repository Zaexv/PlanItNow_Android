package com.planitnow.usecases.addfriend

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.R
import com.planitnow.backend.ApolloMutationHandler

class AddFriendViewModel : ViewModel() {

    suspend fun sendFriendRequest(context: Context, username: String){
        var res = try {
            ApolloMutationHandler.sendFriendRequest(username)
        } catch(e: ApolloException){
            Toast.makeText(context,e.message,Toast.LENGTH_SHORT).show()
            return
        }
        if(res.hasErrors()){
            Toast.makeText(context, R.string.error_friend_request,Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, R.string.success_friend_request, Toast.LENGTH_SHORT).show()
        }
    }

}
