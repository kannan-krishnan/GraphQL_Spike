package com.orane.icliniq

import androidx.lifecycle.ViewModel

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    suspend fun login(email: String, password: String): LoginMutation.Data? {
        return authRepository.loginUser(email, password)
    }
}
