package com.planitnow.backend

import android.content.SharedPreferences
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import com.graphql.models.AllPlansQuery
import com.graphql.models.DetailedPlanQuery
import com.graphql.models.MeQuery
import com.graphql.models.MyFriendsQuery
import com.planitnow.model.session.Session

object ApolloQueryHandler {

    suspend fun getAllPlans(): ApolloResponse<AllPlansQuery.Data> {
        return ApolloClientSingleton.instance.apolloClient.query(AllPlansQuery())
    }

    suspend fun getDetailedPlan(id: Int): ApolloResponse<DetailedPlanQuery.Data> {
        return ApolloClientSingleton.instance.apolloClient.query(DetailedPlanQuery(id))
    }

    suspend fun getLoggedUser(): ApolloResponse<MeQuery.Data> {
        return ApolloClientSingleton.instance.apolloClient.query(MeQuery())
    }

    suspend fun getMyFriends(): ApolloResponse<MyFriendsQuery.Data>{
        return ApolloClientSingleton.instance.apolloClient.query(MyFriendsQuery())
    }


}