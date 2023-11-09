package com.orane.icliniq

import android.util.Log
import com.apollographql.apollo3.exception.ApolloException
import com.orane.icliniq.apollo.apolloClient


class AuthRepository() {
    suspend fun loginUser(email: String, password: String): LoginWithTokenMutation.Data? {
        val loginMutation = LoginWithTokenMutation(email, password)
        val response = try {
            apolloClient.mutation(loginMutation).execute()
        } catch (e: ApolloException) {
            throw e
        }
        return response.data
    }
//    suspend fun loginWithToken(email: String): Login.Data? {
    suspend fun login(email: String): LoginMutation.Data? {
        val response = try {
             apolloClient.mutation(LoginMutation(email=email)).execute()
        } catch (e: ApolloException) {
            throw e
        }
        if (response.hasErrors()) {
            Log.w("Login", "Failed to login: ${response.errors?.get(0)?.message}")
        }
        Log.e("TAG", "loginWithToken: "+response.data?.login?.token)
        return response.data
    }
}

