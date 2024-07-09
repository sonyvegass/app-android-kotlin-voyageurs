package com.example.myapplicationpa2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplicationpa2.model.Reservation
import com.example.myapplicationpa2.repository.ReservationsRepository

class ReservationsViewModel : ViewModel() {

    private val _reservations = MutableLiveData<List<Reservation>>()
    val reservations: LiveData<List<Reservation>> = _reservations

    private val reservationsRepository = ReservationsRepository()

    fun fetchUserReservations(userId: String) {
        reservationsRepository.fetchUserReservations(userId) { reservations ->
            _reservations.postValue(reservations)
        }
    }
}
