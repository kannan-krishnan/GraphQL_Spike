package com.orane.icliniq.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private val okHttpClient = OkHttpClient.Builder().build()

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .okHttpClient(okHttpClient)
    .build()