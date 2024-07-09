package com.example.myapplicationpa2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplicationpa2.model.LoginResult
import com.example.myapplicationpa2.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.emailEditText)
        editTextPassword = findViewById(R.id.passwordEditText)
        buttonLogin = findViewById(R.id.loginButton)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this@LoginActivity, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        val call = RetrofitClient.instance.login(email, password)

        call.enqueue(object : Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                if (response.isSuccessful) {
                    val loginResult = response.body()

                    if (loginResult != null && loginResult.success) {
                        // Succès de l'authentification
                        val userId = loginResult.userId
                        val token = loginResult.token

                        // Stocker le token dans RetrofitClient pour les futures requêtes
                        RetrofitClient.token = token

                        // Rediriger vers l'activité de profil en passant l'ID de l'utilisateur
                        val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                        intent.putExtra("userId", userId)
                        startActivity(intent)
                        finish()
                    } else {
                        // Échec de l'authentification
                        Toast.makeText(this@LoginActivity, "Login failed: ${loginResult?.errorMessage}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Échec de la connexion. Veuillez réessayer.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.e("LoginActivity", "Erreur de réseau: ${t.message}", t)
                Toast.makeText(this@LoginActivity, "Erreur de réseau. Veuillez vérifier votre connexion.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
