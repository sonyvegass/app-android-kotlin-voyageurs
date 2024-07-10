package com.example.myapplicationpa2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationpa2.model.Reservation
import com.example.myapplicationpa2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ReservationsActivity : AppCompatActivity() {

    private lateinit var reservationsRecyclerView: RecyclerView
    private lateinit var reservationsAdapter: ReservationsAdapter
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservations)

        reservationsRecyclerView = findViewById(R.id.reservationsRecyclerView)
        reservationsRecyclerView.layoutManager = LinearLayoutManager(this)

        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        errorTextView = findViewById(R.id.errorTextView)

        val userId = intent.getStringExtra("userId")
        if (!userId.isNullOrEmpty()) {
            getReservations(userId)
        } else {
            errorTextView.text = "UserId manquant."
            errorTextView.visibility = View.VISIBLE
        }
    }

    private fun getReservations(userId: String) {
        loadingProgressBar.visibility = View.VISIBLE
        errorTextView.visibility = View.GONE
        reservationsRecyclerView.visibility = View.GONE

        val call = RetrofitClient.instance.getUserReservations(userId)
        call.enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                loadingProgressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val reservations = response.body() ?: emptyList()
                    if (reservations.isNotEmpty()) {
                        reservationsAdapter = ReservationsAdapter()
                        reservationsRecyclerView.adapter = reservationsAdapter
                        reservationsAdapter.submitList(reservations) // Submit the list to adapter
                        reservationsRecyclerView.visibility = View.VISIBLE

                        for (reservation in reservations) {
                            Log.d("ReservationsActivity", "Reservation ID: ${reservation.id}")
                            Log.d("ReservationsActivity", "NumAdults: ${reservation.numAdults}")
                            Log.d("ReservationsActivity", "NumChildren: ${reservation.numChildren}")
                            Log.d("ReservationsActivity", "NumInfants: ${reservation.numInfants}")
                            Log.d("ReservationsActivity", "TotalPrice: ${reservation.totalPrice}")
                            Log.d("ReservationsActivity", "Dates: ${reservation.dates}")
                        }
                    } else {
                        errorTextView.text = "Aucune réservation trouvée."
                        errorTextView.visibility = View.VISIBLE
                    }
                } else {
                    errorTextView.text = "Erreur lors du chargement des réservations."
                    errorTextView.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                loadingProgressBar.visibility = View.GONE
                errorTextView.text = "Erreur de réseau. Veuillez vérifier votre connexion."
                errorTextView.visibility = View.VISIBLE

                Log.e("ReservationsActivity", "Erreur de réseau: ${t.message}", t)
            }
        })
    }
}
