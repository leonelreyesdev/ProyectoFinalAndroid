package com.example.proyectofinalandroid.TicTacToe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.R

class TicTacToeActivity : AppCompatActivity() {
    private lateinit var statusText: TextView
    private lateinit var buttons: Array<Array<Button>>
    private var currentPlayer = "X"
    private var board = Array(3) { Array(3) { "" } }
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        statusText = findViewById(R.id.tvStatus)
        buttons = Array(3) { row ->
            Array(3) { col ->
                val id = resources.getIdentifier("btn$row$col", "id", packageName)
                findViewById<Button>(id).apply {
                    setOnClickListener { onCellClicked(row, col) }
                }
            }
        }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            resetGame()
        }

        updateStatus()
    }

    private fun onCellClicked(row: Int, col: Int) {
        if (board[row][col].isEmpty() && !gameOver) {
            board[row][col] = currentPlayer
            buttons[row][col].text = currentPlayer
            if (checkWin()) {
                statusText.text = "¡Ganó $currentPlayer!"
                gameOver = true
            } else if (checkDraw()) {
                statusText.text = "¡Empate!"
                gameOver = true
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateStatus()
            }
        }
    }

    private fun updateStatus() {
        statusText.text = "Turno de $currentPlayer"
    }

    private fun checkWin(): Boolean {
        val lines = listOf(
            // Filas
            listOf(board[0][0], board[0][1], board[0][2]),
            listOf(board[1][0], board[1][1], board[1][2]),
            listOf(board[2][0], board[2][1], board[2][2]),
            // Columnas
            listOf(board[0][0], board[1][0], board[2][0]),
            listOf(board[0][1], board[1][1], board[2][1]),
            listOf(board[0][2], board[1][2], board[2][2]),
            // Diagonales
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )
        return lines.any { it.all { cell -> cell == currentPlayer } }
    }

    private fun checkDraw(): Boolean {
        return board.all { row -> row.all { cell -> cell.isNotEmpty() } }
    }

    private fun resetGame() {
        board = Array(3) { Array(3) { "" } }
        currentPlayer = "X"
        gameOver = false
        for (row in 0..2) {
            for (col in 0..2) {
                buttons[row][col].text = ""
            }
        }
        updateStatus()
    }
}