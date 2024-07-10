package com.example.myapplicationpa2.model

data class Reservation(
    val id: String,
    val dates: List<String>, // Pour représenter les dates comme une liste de chaînes
    val numAdults: Int,
    val numChildren: Int,
    val numInfants: Int, // Changement de numBabies à numInfants pour correspondre à l'API
    val totalPrice: Double
)
