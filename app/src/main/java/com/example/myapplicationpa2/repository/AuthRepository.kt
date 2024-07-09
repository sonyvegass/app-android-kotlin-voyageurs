package com.example.myapplicationpa2.repository

import com.example.myapplicationpa2.network.RetrofitClient
import com.example.myapplicationpa2.model.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    fun login(email: String, password: String, callback: (LoginResult) -> Unit) {
        val call = RetrofitClient.instance.login(email, password)
        call.enqueue(object : Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { result ->
                        callback(
                            LoginResult(
                                success = result.success,
                                token = result.token,
                                userId = result.userId,
                                errorMessage = null
                            )
                        )
                    } ?: run {
                        callback(
                            LoginResult(
                                success = false,
                                token = null,
                                userId = null,
                                errorMessage = "Login failed"
                            )
                        )
                    }
                } else {
                    callback(
                        LoginResult(
                            success = false,
                            token = null,
                            userId = null,
                            errorMessage = "Login failed"
                        )
                    )
                }
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                callback(
                    LoginResult(
                        success = false,
                        token = null,
                        userId = null,
                        errorMessage = t.message ?: "Unknown error"
                    )
                )
            }
        })
    }
}
