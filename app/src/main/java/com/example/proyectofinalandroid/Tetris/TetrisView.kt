package com.example.proyectofinalandroid.Tetris

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View

class TetrisView : View, Runnable {

    private var game = TetrisGame()
    private val handler = Handler(Looper.getMainLooper())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var cellSize = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    private var onScoreUpdate: ((Int) -> Unit)? = null
    private var onGameOver: (() -> Unit)? = null

    // Constructor para XML
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initGame()
    }

    // Constructor opcional para uso programático
    constructor(context: Context) : super(context) {
        initGame()
    }

    private fun initGame() {
        handler.postDelayed(this, 500)
    }

    fun setCallbacks(onScoreUpdate: (Int) -> Unit, onGameOver: () -> Unit) {
        this.onScoreUpdate = onScoreUpdate
        this.onGameOver = onGameOver
    }

    fun resetGame() {
        game = TetrisGame()
        removeCallbacks()
        handler.postDelayed(this, 500)
        invalidate()
    }

    fun removeCallbacks() {
        handler.removeCallbacks(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val boardWidth = game.cols
        val boardHeight = game.rows

        cellSize = minOf(w.toFloat() / boardWidth, h.toFloat() / boardHeight)

        val totalBoardWidth = cellSize * boardWidth
        val totalBoardHeight = cellSize * boardHeight

        offsetX = (w - totalBoardWidth) / 2f
        offsetY = (h - totalBoardHeight) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Dibujar tablero
        for (r in 0 until game.rows) {
            for (c in 0 until game.cols) {
                val value = game.board[r][c]
                paint.color = if (value != 0) value else Color.parseColor("#1A1A1A")
                val left = offsetX + c * cellSize
                val top = offsetY + r * cellSize
                val right = left + cellSize
                val bottom = top + cellSize
                canvas.drawRect(left, top, right, bottom, paint)
            }
        }

        // Dibujar pieza activa
        val shape = game.current.shape
        for (i in shape.indices) {
            for (j in shape[i].indices) {
                if (shape[i][j] != 0) {
                    paint.color = game.current.color
                    val x = game.x + j
                    val y = game.y + i
                    val left = offsetX + x * cellSize
                    val top = offsetY + y * cellSize
                    val right = left + cellSize
                    val bottom = top + cellSize
                    canvas.drawRect(left, top, right, bottom, paint)
                }
            }
        }
    }

    override fun run() {
        if (game.isGameOver) {
            handler.removeCallbacks(this)
            onGameOver?.invoke()
            return
        }

        game.move(Direction.DOWN)
        onScoreUpdate?.invoke(game.score)
        invalidate()
        handler.postDelayed(this, 500)
    }

    fun moveLeft() {
        game.move(Direction.LEFT)
    }

    fun moveRight() {
        game.move(Direction.RIGHT)
    }

    fun moveDown() {
        game.move(Direction.DOWN)
    }

    fun rotate() {
        game.rotate()
    }
}