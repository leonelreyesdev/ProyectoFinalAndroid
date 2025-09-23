package com.example.proyectofinalandroid.pacman

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.R

class PacmanActivity : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacman)

        gameView = findViewById(R.id.gameView)

        // Controles de movimiento
        findViewById<Button>(R.id.btnUp).setOnClickListener { gameView.moveUp() }
        findViewById<Button>(R.id.btnDown).setOnClickListener { gameView.moveDown() }
        findViewById<Button>(R.id.btnLeft).setOnClickListener { gameView.moveLeft() }
        findViewById<Button>(R.id.btnRight).setOnClickListener { gameView.moveRight() }

        // Botón de reinicio
        findViewById<Button>(R.id.btnRestartGame).setOnClickListener {
            gameView.resetGame()
        }
    }
}
