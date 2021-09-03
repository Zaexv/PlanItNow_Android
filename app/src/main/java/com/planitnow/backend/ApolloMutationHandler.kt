package com.planitnow.backend

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.graphql.models.*
import com.planitnow.model.session.Session

object ApolloMutationHandler {

    suspend fun createPlan(
        title: String,
        description: String,
        location: String,
        initHour: String,
        endHour: String,
        initDate: String,
        isPublic: Boolean,
        urlPlanPicture: String,
        maxParticipants: Int,
    ): ApolloResponse<CreatePlanMutation.Data> {
        return ApolloClientSingleton.instance.apolloClient.mutate(
            CreatePlanMutation(
                description = description,
                title = title,
                location = location,
                initHour = initHour,
                endHour = endHour,
                initDate = initDate,
                maxParticipants = Optional.Present(maxParticipants),
                isPublic = Optional.Present(isPublic),
                urlPlanPicture = Optional.Present(urlPlanPicture)
            )
        )
    }

    suspend fun deletePlan(id: Int): ApolloResponse<DeletePlanMutation.Data> {
        return ApolloClientSingleton.instance.apolloClient.mutate(
            DeletePlanMutation(
                id = id
            )
        )
    }

    suspend fun sendFriendRequest(toUsername: String): ApolloResponse<SendFriendRequestMutation.Data>{
        return ApolloClientSingleton.instance.apolloClient.mutate(
            SendFriendRequestMutation(
                toUsername = toUsername
            )
        )
    }

    suspend fun acceptFriendRequest(frId: Int): ApolloResponse<AcceptFriendRequestMutation.Data>{
        return ApolloClientSingleton.instance.apolloClient.mutate(
            AcceptFriendRequestMutation(frId = frId)
        )
    }

    suspend fun rejectFriendRequest(frId: Int): ApolloResponse<RejectFriendRequestMutation.Data>{
        return ApolloClientSingleton.instance.apolloClient.mutate(
            RejectFriendRequestMutation(frId = frId)
        )
    }

    suspend fun participateInPlan(planId: Int): ApolloResponse<ParticipateInPlanMutation.Data>{
        return ApolloClientSingleton.instance.apolloClient.mutate(
            ParticipateInPlanMutation(planId = planId)
        )
    }

    suspend fun editProfile(publicUsername: String, biography: String,
                            residence: String, urlProfilePicture: String): ApolloResponse<EditProfileMutation.Data> {
        return ApolloClientSingleton.instance.apolloClient.mutate(
            EditProfileMutation(
                publicUsername = Optional.Present(publicUsername),
                biography = Optional.Present(biography),
                residence = Optional.Present(residence),
                urlProfilePicture = Optional.Present(urlProfilePicture),
            )
        )
    }

}