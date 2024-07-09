package com.example.myapplicationpa2.repository

import com.example.myapplicationpa2.network.RetrofitClient
import com.example.myapplicationpa2.model.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationsRepository {

    fun fetchUserReservations(userId: String, callback: (List<Reservation>?) -> Unit) {
        val call = RetrofitClient.instance.getUserReservations(userId)
        call.enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                callback(null)
            }
        })
    }
}
