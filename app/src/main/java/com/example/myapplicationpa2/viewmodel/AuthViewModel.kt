package com.example.myapplicationpa2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationpa2.model.LoginResult
import com.example.myapplicationpa2.repository.AuthRepository

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val authRepository = AuthRepository()

    fun login(email: String, password: String) {
        authRepository.login(email, password) { result ->
            _loginResult.postValue(result)
        }
    }
}
