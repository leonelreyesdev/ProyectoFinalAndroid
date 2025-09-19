package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import data.DatabaseHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        db = DatabaseHelper(this)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnGoToRegister = findViewById<Button>(R.id.btnGoToRegister)

        btnLogin.setOnClickListener {
            val user = etUsername.text.toString()
            val pass = etPassword.text.toString()
            if (db.loginUser(user, pass)) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("username", user)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}