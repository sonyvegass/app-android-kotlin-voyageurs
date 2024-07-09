package com.example.myapplicationpa2.repository

import com.example.myapplicationpa2.model.User
import com.example.myapplicationpa2.model.Reservation
import com.example.myapplicationpa2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun fetchUserProfile(userId: String, callback: (User?) -> Unit) {
        val call = RetrofitClient.instance.getUserProfile(userId)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    callback(user)
                } else {
                    callback(null) // Handle error case
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(null) // Handle failure case
            }
        })
    }

    fun fetchUserReservations(userId: String, callback: (List<Reservation>?) -> Unit) {
        val call = RetrofitClient.instance.getUserReservations(userId)
        call.enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(
                call: Call<List<Reservation>>,
                response: Response<List<Reservation>>
            ) {
                if (response.isSuccessful) {
                    val reservations = response.body()
                    callback(reservations)
                } else {
                    callback(null) // Handle error case
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                callback(null) // Handle failure case
            }
        })
    }
}
