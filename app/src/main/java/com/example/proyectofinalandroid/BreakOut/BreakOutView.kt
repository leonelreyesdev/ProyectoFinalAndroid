package com.example.proyectofinalandroid.BreakOut

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

class BreakoutView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val ballPaint = Paint().apply { color = Color.WHITE }
    private val paddlePaint = Paint().apply { color = Color.BLUE }
    private val brickPaint = Paint().apply { color = Color.RED }
    private val textPaint = Paint().apply {
        color = Color.YELLOW
        textSize = 50f
        isFakeBoldText = true
    }

    private var ballX = 300f
    private var ballY = 600f
    private val ballRadius = 20f
    private var ballDX = 8f
    private var ballDY = -8f

    private var paddleX = 250f
    private val paddleY = 1000f
    private val paddleWidth = 200f
    private val paddleHeight = 30f

    private val bricks = mutableListOf<RectF>()
    private var gameOver = false
    private var score = 0
    private var gameStarted = false

    init {
        resetGame()
    }

    fun resetGame() {
        ballX = 300f
        ballY = 600f
        ballDX = 8f
        ballDY = -8f
        paddleX = 250f
        score = 0
        gameOver = false
        gameStarted = false
        bricks.clear()

        for (row in 0..2) {
            for (col in 0..5) {
                val left = 50f + col * 120
                val top = 100f + row * 60
                val right = left + 100
                val bottom = top + 40
                bricks.add(RectF(left, top, right, bottom))
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.BLACK)

        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint)
        canvas.drawRect(paddleX, paddleY, paddleX + paddleWidth, paddleY + paddleHeight, paddlePaint)

        bricks.forEach {
            canvas.drawRect(it, brickPaint)
        }

        canvas.drawText("Puntos: $score", 50f, 80f, textPaint)

        if (!gameOver && gameStarted) {
            updateBall()
            checkCollisions()
        }

        if (gameOver) {
            canvas.drawText("¡Perdiste!", width / 2f - 100, height / 2f, textPaint)
        }

        invalidate()
    }

    private fun updateBall() {
        ballX += ballDX
        ballY += ballDY

        if (ballX <= ballRadius || ballX >= width - ballRadius) ballDX *= -1
        if (ballY <= ballRadius) ballDY *= -1
        if (ballY >= height) gameOver = true
    }

    private fun checkCollisions() {
        val paddleRect = RectF(paddleX, paddleY, paddleX + paddleWidth, paddleY + paddleHeight)
        if (paddleRect.contains(ballX, ballY + ballRadius)) {
            ballDY *= -1
            ballY = paddleY - ballRadius
        }

        val iterator = bricks.iterator()
        while (iterator.hasNext()) {
            val brick = iterator.next()
            if (brick.contains(ballX, ballY)) {
                iterator.remove()
                ballDY *= -1
                score++
                break
            }
        }

        if (bricks.isEmpty()) {
            gameOver = true
        }
    }

    fun movePaddleLeft() {
        paddleX = (paddleX - 40).coerceAtLeast(0f)
        gameStarted = true
    }

    fun movePaddleRight() {
        paddleX = (paddleX + 40).coerceAtMost(width - paddleWidth)
        gameStarted = true
    }
}