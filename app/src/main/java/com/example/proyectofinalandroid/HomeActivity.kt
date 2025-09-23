package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.pacman.PacmanActivity
import com.example.proyectofinalandroid.TicTacToe.TicTacToeActivity
import com.example.proyectofinalandroid.Memory.MemoryGameActivity
import com.example.proyectofinalandroid.BreakOut.BreakoutActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val username = intent.getStringExtra("username")
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "¡Bienvenido, $username!"

        // Juego 1: Pac-Man
        findViewById<Button>(R.id.btnJuego1).setOnClickListener {
            startActivity(Intent(this, PacmanActivity::class.java))
        }

        // Juego 2: Tres en Raya
        findViewById<Button>(R.id.btnJuego2).setOnClickListener {
            startActivity(Intent(this, TicTacToeActivity::class.java))
        }

        // Juego 3: Memorama
        findViewById<Button>(R.id.btnJuego3).setOnClickListener {
            startActivity(Intent(this, MemoryGameActivity::class.java))
        }

        // Juego 4: Breakout
        findViewById<Button>(R.id.btnJuego4).setOnClickListener {
            startActivity(Intent(this, BreakoutActivity::class.java))
        }

        // Juegos 5 a 6: aún no implementados
        findViewById<Button>(R.id.btnJuego5).setOnClickListener {
            Toast.makeText(this, "Juego 5 aún no disponible", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.btnJuego6).setOnClickListener {
            Toast.makeText(this, "Juego 6 aún no disponible", Toast.LENGTH_SHORT).show()
        }
    }
}