package com.example.proyectofinalandroid

import data.DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        db = DatabaseHelper(this)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnGoToLogin = findViewById<Button>(R.id.btnGoToLogin)

        btnRegister.setOnClickListener {
            val user = etUsername.text.toString()
            val pass = etPassword.text.toString()
            if (db.registerUser(user, pass)) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        }

        btnGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}