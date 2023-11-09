package com.orane.icliniq

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

      val _sampleLiveData = MutableLiveData<String>()

    suspend fun loginWithOut(email: String, password: String): LoginWithTokenMutation.Data? {
        return authRepository.loginUser(email, password)
    }
    suspend fun loginWithToken(email: String):Boolean {
        val response= authRepository.login(email)

        Log.d("TAG", "loginWithToken: "+response?.login?.token)
        _sampleLiveData.postValue(response?.login?.token)
        return response?.login?.token != null
    }
}
