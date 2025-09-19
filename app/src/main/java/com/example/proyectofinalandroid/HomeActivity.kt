package com.example.proyectofinalandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val username = intent.getStringExtra("username")
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "¡Bienvenido, $username!"

        val buttons = listOf(
            R.id.btnJuego1, R.id.btnJuego2, R.id.btnJuego3,
            R.id.btnJuego4, R.id.btnJuego5, R.id.btnJuego6
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                Toast.makeText(this, "Abrir juego ${resources.getResourceEntryName(id)}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}