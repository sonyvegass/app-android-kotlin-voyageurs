package com.example.myapplicationpa2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationpa2.model.User
import com.example.myapplicationpa2.model.Reservation
import com.example.myapplicationpa2.repository.UserRepository

class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> = _reservations

    fun fetchUserProfile(userId: String) {
        userRepository.fetchUserProfile(userId) { user ->
            _user.postValue(user)
        }
    }

    fun fetchUserReservations(userId: String) {
        userRepository.fetchUserReservations(userId) { reservations ->
            _reservations.postValue(reservations)
        }
    }
}
