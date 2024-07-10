package com.example.myapplicationpa2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationpa2.R
import com.example.myapplicationpa2.viewmodel.UserViewModel
import com.example.myapplicationpa2.viewmodel.ReservationsViewModel
import com.example.myapplicationpa2.ReservationsAdapter

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels()
    private val reservationsViewModel: ReservationsViewModel by viewModels()

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var reservationsRecyclerView: RecyclerView
    private lateinit var reservationsAdapter: ReservationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userNameTextView = findViewById(R.id.userNameTextView)
        userEmailTextView = findViewById(R.id.userEmailTextView)
        reservationsRecyclerView = findViewById(R.id.reservationsRecyclerView)

        reservationsRecyclerView.layoutManager = LinearLayoutManager(this)
        reservationsAdapter = ReservationsAdapter()
        reservationsRecyclerView.adapter = reservationsAdapter

        userViewModel.user.observe(this, Observer { user ->
            user?.let {
                userNameTextView.text = it.nom
                userEmailTextView.text = it.email
            }
        })

        reservationsViewModel.reservations.observe(this, Observer { reservations ->
            reservations?.let {
                reservationsAdapter.submitList(it)
            }
        })

        // Retrieve userId from SharedPreferences
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", null)

        if (userId != null) {
            userViewModel.fetchUserProfile(userId)
            reservationsViewModel.fetchUserReservations(userId)
        } else {
            // Gestion du cas où userId est null, par exemple rediriger vers l'écran de connexion
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Optionnel : termine l'activité actuelle pour empêcher le retour avec le bouton Back
        }
    }
}
