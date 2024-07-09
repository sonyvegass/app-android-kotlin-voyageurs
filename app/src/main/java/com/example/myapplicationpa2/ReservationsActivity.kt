package com.example.myapplicationpa2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationpa2.viewmodel.ReservationsViewModel

class ReservationsActivity : AppCompatActivity() {

    private lateinit var reservationsViewModel: ReservationsViewModel
    private lateinit var reservationsTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations)

        reservationsTextView = findViewById(R.id.reservationsTextView)

        reservationsViewModel = ViewModelProvider(this).get(ReservationsViewModel::class.java)

        // Retrieve userId from SharedPreferences
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        userId?.let {
            reservationsViewModel.fetchUserReservations(it) // Fetch reservations for the retrieved userId
        }

        reservationsViewModel.reservations.observe(this, Observer { reservations ->
            reservations?.let {
                if (it.isEmpty()) {
                    reservationsTextView.text = "Aucune réservation trouvée."
                } else {
                    reservationsTextView.text = it.joinToString("\n") { reservation ->
                        "Reservation: ${reservation.id}"
                        // Ici, vous pouvez personnaliser l'affichage des détails de réservation selon vos besoins
                    }
                }
            }
        })
    }
}
