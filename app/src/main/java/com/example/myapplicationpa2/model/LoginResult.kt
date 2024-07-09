package com.example.myapplicationpa2.model

data class LoginResult(
    val success: Boolean,
    val token: String?,
    val userId: String?,
    val errorMessage: String? = null
)
