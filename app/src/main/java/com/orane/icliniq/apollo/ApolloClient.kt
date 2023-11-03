package com.orane.icliniq.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

object ApolloClient {
    private val okHttpClient = OkHttpClient.Builder().build()

    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://api.mocki.io/v2/c4d7a195/graphql")
        .okHttpClient(okHttpClient)
        .build()
}
