package com.orane.icliniq

import com.apollographql.apollo3.exception.ApolloException
import com.orane.icliniq.apollo.ApolloClient


class AuthRepository() {
    suspend fun loginUser(email: String, password: String): LoginMutation.Data? {
        val loginMutation = LoginMutation(email, password)
        val response = try {
            ApolloClient.apolloClient.mutation(loginMutation).execute()
        } catch (e: ApolloException) {
            throw e
        }
        return response.data
    }
}

