package com.example.proyectofinalandroid.Tetris

import android.graphics.Color
import kotlin.random.Random

class TetrisGame(val rows: Int = 20, val cols: Int = 10) {
    val board = Array(rows) { IntArray(cols) }
    var current: Tetromino = generateRandomPiece()
    var x = cols / 2 - 2
    var y = 0
    var isGameOver = false
    var score = 0

    fun move(dir: Direction) {
        if (isGameOver) return
        val newX = when (dir) {
            Direction.LEFT -> x - 1
            Direction.RIGHT -> x + 1
            Direction.DOWN -> x
        }
        val newY = if (dir == Direction.DOWN) y + 1 else y
        if (!collides(current.shape, newX, newY)) {
            x = newX
            y = newY
        } else if (dir == Direction.DOWN) {
            merge()
            clearLines()
            spawn()
        }
    }

    fun rotate() {
        val rotated = current.rotate()
        if (!collides(rotated.shape, x, y)) {
            current = rotated
        }
    }

    private fun collides(shape: Array<IntArray>, px: Int, py: Int): Boolean {
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] != 0) {
                    val nx = px + j
                    val ny = py + i
                    if (nx !in 0 until cols || ny !in 0 until rows || board[ny][nx] != 0) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun merge() {
        for (i in current.shape.indices) {
            for (j in current.shape[i].indices) {
                if (current.shape[i][j] != 0) {
                    val nx = x + j
                    val ny = y + i
                    if (ny in 0 until rows && nx in 0 until cols) {
                        board[ny][nx] = current.color
                    }
                }
            }
        }
    }

    private fun clearLines() {
        val newBoard = board.filter { row -> row.any { it == 0 } }.toMutableList()
        val linesCleared = rows - newBoard.size
        repeat(linesCleared) { newBoard.add(0, IntArray(cols)) }
        for (i in 0 until rows) board[i] = newBoard[i]
        score += linesCleared * 100
    }

    private fun spawn() {
        current = generateRandomPiece()
        x = cols / 2 - 2
        y = 0
        if (collides(current.shape, x, y)) {
            isGameOver = true
        }
    }

    private fun generateRandomPiece(): Tetromino {
        val pieces = listOf(
            Tetromino(arrayOf( // I
                intArrayOf(0, 0, 0, 0),
                intArrayOf(1, 1, 1, 1),
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 0, 0, 0)
            ), Color.CYAN),

            Tetromino(arrayOf( // O
                intArrayOf(0, 0, 0, 0),
                intArrayOf(0, 1, 1, 0),
                intArrayOf(0, 1, 1, 0),
                intArrayOf(0, 0, 0, 0)
            ), Color.YELLOW),

            Tetromino(arrayOf( // T
                intArrayOf(0, 0, 0),
                intArrayOf(1, 1, 1),
                intArrayOf(0, 1, 0)
            ), Color.MAGENTA),

            Tetromino(arrayOf( // J
                intArrayOf(1, 0, 0),
                intArrayOf(1, 1, 1),
                intArrayOf(0, 0, 0)
            ), Color.BLUE),

            Tetromino(arrayOf( // L
                intArrayOf(0, 0, 1),
                intArrayOf(1, 1, 1),
                intArrayOf(0, 0, 0)
            ), Color.rgb(255, 165, 0)),

            Tetromino(arrayOf( // S
                intArrayOf(0, 1, 1),
                intArrayOf(1, 1, 0),
                intArrayOf(0, 0, 0)
            ), Color.GREEN),

            Tetromino(arrayOf( // Z
                intArrayOf(1, 1, 0),
                intArrayOf(0, 1, 1),
                intArrayOf(0, 0, 0)
            ), Color.RED)
        )
        return pieces.random()
    }
}