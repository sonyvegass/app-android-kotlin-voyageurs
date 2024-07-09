package com.example.myapplicationpa2.network
import com.example.myapplicationpa2.model.LoginResult
import com.example.myapplicationpa2.model.Reservation
import com.example.myapplicationpa2.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("user/api/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResult>

    @GET("user/api/profile/{userId}")
    fun getUserProfile(@Path("userId") userId: String): Call<User>

    @GET("user/api/reservations/{userId}")
    fun getUserReservations(@Path("userId") userId: String): Call<List<Reservation>>
}
