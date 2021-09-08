package com.planitnow.usecases.profile

import android.content.Context
import android.widget.Toast
import androidx.core.content.res.TypedArrayUtils.getText
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.exception.ApolloException
import com.planitnow.R
import com.planitnow.backend.ApolloLoginHandler
import com.planitnow.backend.ApolloMutationHandler
import com.planitnow.model.session.Session

class ProfileViewModel : ViewModel() {

    suspend fun editProfile(
        context: Context,
        publicUsername: String,
        biography: String,
        residence: String,
        urlProfilePicture: String,
    ): Boolean {
        var res = try {
            ApolloMutationHandler.editProfile(
                publicUsername,
                biography,
                residence,
                urlProfilePicture
            )
        } catch (e: ApolloException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            return false
        }

        if (res.hasErrors()) {
            Toast.makeText(context, res.errors!![0].message, Toast.LENGTH_SHORT).show()
        } else {
            Session.instance.refreshMe()
            Toast.makeText(
                context, context.getText(R.string.success_edit_profile),
                Toast.LENGTH_SHORT
            ).show()
            return true
        }
        return false
    }

}