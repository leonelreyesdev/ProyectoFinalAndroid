package com.example.proyectofinalandroid.pacman

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import kotlin.math.sqrt
import kotlin.random.Random

class GameView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val pacmanPaint = Paint().apply { color = Color.YELLOW }
    private val dotPaint = Paint().apply { color = Color.WHITE }
    private val ghostPaint = Paint().apply { color = Color.RED }
    private val scorePaint = Paint().apply {
        color = Color.WHITE
        textSize = 50f
        isFakeBoldText = true
    }

    private var pacmanX = 100f
    private var pacmanY = 100f
    private val pacmanSize = 60f

    private val dots = mutableListOf<Pair<Float, Float>>()
    private val ghosts = mutableListOf<Pair<Float, Float>>()
    private var score = 0
    private var gameOver = false

    init {
        resetGame()
    }

    private fun resetGame() {
        pacmanX = 100f
        pacmanY = 100f
        score = 0
        gameOver = false
        dots.clear()
        ghosts.clear()

        repeat(10) {
            dots.add(Pair(Random.nextInt(100, 800).toFloat(), Random.nextInt(100, 1200).toFloat()))
        }

        repeat(3) {
            ghosts.add(Pair(Random.nextInt(200, 800).toFloat(), Random.nextInt(200, 1200).toFloat()))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.BLACK) // Fondo negro

        // Dibujar Pac-Man
        canvas.drawCircle(pacmanX, pacmanY, pacmanSize, pacmanPaint)

        // Dibujar puntos
        dots.forEach {
            canvas.drawCircle(it.first, it.second, 15f, dotPaint)
        }

        // Dibujar fantasmas
        ghosts.forEach {
            canvas.drawCircle(it.first, it.second, 40f, ghostPaint)
        }

        // HUD
        canvas.drawText("Puntos: $score / ${score + dots.size}", 50f, 80f, scorePaint)

        // Verificar colisiones
        if (!gameOver) {
            checkDotCollision()
            checkGhostCollision()
            moveGhosts()
        }

        // Mensaje de victoria
        if (dots.isEmpty() && !gameOver) {
            canvas.drawText("¡Ganaste!", width / 2f - 100, height / 2f, scorePaint)
            gameOver = true
        }

        invalidate()
    }

    private fun checkDotCollision() {
        val iterator = dots.iterator()
        while (iterator.hasNext()) {
            val dot = iterator.next()
            val dx = pacmanX - dot.first
            val dy = pacmanY - dot.second
            val distance = sqrt(dx * dx + dy * dy)
            if (distance < pacmanSize + 15) {
                iterator.remove()
                score++
            }
        }
    }

    private fun checkGhostCollision() {
        for (ghost in ghosts) {
            val dx = pacmanX - ghost.first
            val dy = pacmanY - ghost.second
            val distance = sqrt(dx * dx + dy * dy)
            if (distance < pacmanSize + 40) {
                Toast.makeText(context, "¡Te atraparon!", Toast.LENGTH_SHORT).show()
                resetGame()
                break
            }
        }
    }

    private fun moveGhosts() {
        for (i in ghosts.indices) {
            val dx = Random.nextInt(-10, 10)
            val dy = Random.nextInt(-10, 10)
            val newX = ghosts[i].first + dx
            val newY = ghosts[i].second + dy
            ghosts[i] = Pair(newX.coerceIn(0f, width.toFloat()), newY.coerceIn(0f, height.toFloat()))
        }
    }

    // Controles de movimiento
    fun moveUp() { if (!gameOver) pacmanY -= 30 }
    fun moveDown() { if (!gameOver) pacmanY += 30 }
    fun moveLeft() { if (!gameOver) pacmanX -= 30 }
    fun moveRight() { if (!gameOver) pacmanX += 30 }
}