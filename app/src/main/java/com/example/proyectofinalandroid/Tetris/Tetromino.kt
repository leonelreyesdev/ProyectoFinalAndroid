package com.example.proyectofinalandroid.Tetris

data class Tetromino(val shape: Array<IntArray>, val color: Int) {
    fun rotate(): Tetromino {
        val size = shape.size
        val rotated = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in 0 until size) {
                rotated[j][size - 1 - i] = shape[i][j]
            }
        }
        return Tetromino(rotated, color)
    }
}