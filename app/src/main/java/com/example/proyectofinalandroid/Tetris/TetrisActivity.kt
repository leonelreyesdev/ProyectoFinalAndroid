package com.example.proyectofinalandroid.Tetris

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.R

class TetrisActivity : AppCompatActivity() {
    private lateinit var tetrisView: TetrisView
    private lateinit var tvScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tetris)

        // Referencias del layout
        tetrisView = findViewById(R.id.tetrisView)
        tvScore = findViewById(R.id.tvScoreTetris)

        // Conectar callbacks
        tetrisView.setCallbacks(
            onScoreUpdate = { score ->
                runOnUiThread { tvScore.text = "Score: $score" }
            },
            onGameOver = {
                runOnUiThread { showGameOverDialog() }
            }
        )

        // Controles
        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            tetrisView.moveLeft()
            tetrisView.invalidate()
        }

        findViewById<Button>(R.id.btnRight).setOnClickListener {
            tetrisView.moveRight()
            tetrisView.invalidate()
        }

        findViewById<Button>(R.id.btnDown).setOnClickListener {
            tetrisView.moveDown()
            tetrisView.invalidate()
        }

        findViewById<Button>(R.id.btnRotate).setOnClickListener {
            tetrisView.rotate()
            tetrisView.invalidate()
        }

        findViewById<Button>(R.id.btnRestartTetris).setOnClickListener {
            restartGame()
        }
    }

    private fun restartGame() {
        tetrisView.resetGame()
        tvScore.text = "Score: 0"
    }

    private fun showGameOverDialog() {
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage("¿Quieres jugar otra vez?")
            .setPositiveButton("Reiniciar") { _, _ -> restartGame() }
            .setNegativeButton("Salir") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    override fun onPause() {
        super.onPause()
        if (::tetrisView.isInitialized) {
            tetrisView.removeCallbacks()
        }
    }
}