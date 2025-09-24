package com.example.proyectofinalandroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.proyectofinalandroid.pacman.PacmanActivity
import com.example.proyectofinalandroid.TicTacToe.TicTacToeActivity
import com.example.proyectofinalandroid.Memory.MemoryGameActivity
import com.example.proyectofinalandroid.BreakOut.BreakoutActivity
import com.example.proyectofinalandroid.Snake.SnakeActivity
import com.example.proyectofinalandroid.Tetris.TetrisActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Mostrar usuario
        val username = intent.getStringExtra("username") ?: "Jugador"
        findViewById<TextView>(R.id.tvWelcome).text = "¡Bienvenido, $username!"

        // Juego 1: Pac-Man
        findViewById<Button>(R.id.btnJuego1).setOnClickListener {
            startActivity(Intent(this, PacmanActivity::class.java))
        }

        // Juego 2: Tic Tac Toe
        findViewById<Button>(R.id.btnJuego2).setOnClickListener {
            startActivity(Intent(this, TicTacToeActivity::class.java))
        }

        // Juego 3: Memory
        findViewById<Button>(R.id.btnJuego3).setOnClickListener {
            startActivity(Intent(this, MemoryGameActivity::class.java))
        }

        // Juego 4: BreakOut
        findViewById<Button>(R.id.btnJuego4).setOnClickListener {
            startActivity(Intent(this, BreakoutActivity::class.java))
        }

        // Juego 5: Snake
        findViewById<Button>(R.id.btnJuego5).setOnClickListener {
            startActivity(Intent(this, SnakeActivity::class.java))
        }

        // Juego 6: Tetris
        findViewById<Button>(R.id.btnJuego6).setOnClickListener {
            startActivity(Intent(this, TetrisActivity::class.java))
        }
    }
}