package com.example.proyectofinalandroid.Snake

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.R

class SnakeActivity : AppCompatActivity() {
    private lateinit var snakeView: SnakeView
    private lateinit var tvScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake)

        // Referencias del layout: asegúrate que estos IDs existan en activity_snake.xml
        snakeView = findViewById(R.id.snakeView)
        tvScore = findViewById(R.id.tvScoreSnake)

        // Configurar callbacks con tipos explícitos
        snakeView.setCallbacks(
            onScoreUpdate = { score: Int ->
                runOnUiThread { tvScore.text = "Score: $score" }
            },
            onGameOver = { finalScore: Int ->
                runOnUiThread { showGameOverDialog(finalScore) }
            }
        )

        // Controles de dirección
        findViewById<Button>(R.id.btnUp).setOnClickListener {
            snakeView.setDirection(Direction.UP)
        }
        findViewById<Button>(R.id.btnDown).setOnClickListener {
            snakeView.setDirection(Direction.DOWN)
        }
        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            snakeView.setDirection(Direction.LEFT)
        }
        findViewById<Button>(R.id.btnRight).setOnClickListener {
            snakeView.setDirection(Direction.RIGHT)
        }

        // Botón reiniciar opcional
        val btnRestart = findViewById<Button?>(R.id.btnRestartSnake)
        btnRestart?.setOnClickListener {
            restartGame()
        }
    }

    private fun showGameOverDialog(finalScore: Int) {
        AlertDialog.Builder(this)
            .setTitle("Perdiste")
            .setMessage("Puntaje final: $finalScore")
            .setPositiveButton("Reiniciar") { _, _ -> restartGame() }
            .setNegativeButton("Salir") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun restartGame() {
        // resetGame() debe existir en SnakeView y reiniciar el estado sin recrear la vista
        snakeView.resetGame()
        tvScore.text = "Score: 0"
    }

    override fun onPause() {
        super.onPause()
        if (::snakeView.isInitialized) {
            // removeCallbacks() debe existir en SnakeView para parar el handler
            snakeView.removeCallbacks()
        }
    }
}