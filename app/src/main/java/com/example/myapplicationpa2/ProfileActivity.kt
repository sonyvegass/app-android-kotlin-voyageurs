package com.example.myapplicationpa2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplicationpa2.model.User
import com.example.myapplicationpa2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    private lateinit var textViewNom: TextView
    private lateinit var textViewPrenom: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var textViewTelephone: TextView
    private lateinit var buttonReservations: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialisation des vues
        textViewNom = findViewById(R.id.nameTextView)
        textViewPrenom = findViewById(R.id.surnameTextView)
        textViewEmail = findViewById(R.id.emailTextView)
        textViewTelephone = findViewById(R.id.phoneTextView)
        buttonReservations = findViewById(R.id.reservationsButton)

        // Récupération de l'ID de l'utilisateur depuis l'intent
        val userId = intent.getStringExtra("userId")

        // Vérifier si userId est non nul et non vide
        if (!userId.isNullOrEmpty()) {
            getUserProfile(userId)
        } else {
            Toast.makeText(this, "UserId manquant", Toast.LENGTH_SHORT).show()
            finish() // Finir l'activité si userId n'est pas disponible
        }

        // Action du bouton "VOIR MES RÉSERVATIONS"
        buttonReservations.setOnClickListener {
            // Ici tu peux implémenter l'action pour voir les réservations de l'utilisateur
            Toast.makeText(this, "Action à implémenter pour voir les réservations", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserProfile(userId: String) {
        val call = RetrofitClient.instance.getUserProfile(userId)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()

                    if (user != null) {
                        // Afficher les informations du profil de l'utilisateur
                        textViewNom.text = "Nom : ${user.nom}"
                        textViewPrenom.text = "Prénom : ${user.prenom}"
                        textViewEmail.text = "Email : ${user.email}"
                        textViewTelephone.text = "Téléphone : ${user.telephone}"
                    } else {
                        Toast.makeText(this@ProfileActivity, "Utilisateur introuvable", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ProfileActivity, "Erreur de chargement du profil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("ProfileActivity", "Erreur de réseau: ${t.message}", t)
                Toast.makeText(this@ProfileActivity, "Erreur de réseau. Veuillez vérifier votre connexion.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
