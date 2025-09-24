package com.example.proyectofinalandroid.Snake

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View

class SnakeView : View, Runnable {

    private var game = SnakeGame(rows = 20, cols = 20)
    private val handler = Handler(Looper.getMainLooper())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var cellSize = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    private var direction = Direction.RIGHT
    private var onScoreUpdate: ((Int) -> Unit)? = null
    private var onGameOver: ((Int) -> Unit)? = null

    // Constructor para XML
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        startGame()
    }

    // Constructor para uso programático
    constructor(context: Context) : super(context) {
        startGame()
    }

    private fun startGame() {
        handler.postDelayed(this, 250)
    }

    fun setCallbacks(onScoreUpdate: (Int) -> Unit, onGameOver: (Int) -> Unit) {
        this.onScoreUpdate = onScoreUpdate
        this.onGameOver = onGameOver
    }

    fun resetGame() {
        game = SnakeGame(rows = 20, cols = 20)
        direction = Direction.RIGHT
        removeCallbacks()
        handler.postDelayed(this, 250)
        invalidate()
    }

    fun removeCallbacks() {
        handler.removeCallbacks(this)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val boardWidth = game.cols
        val boardHeight = game.rows

        // Calcular tamaño de celda para llenar el espacio disponible (mantener celdas cuadradas)
        cellSize = minOf(w.toFloat() / boardWidth, h.toFloat() / boardHeight)

        val totalBoardWidth = cellSize * boardWidth
        val totalBoardHeight = cellSize * boardHeight

        offsetX = (w - totalBoardWidth) / 2f
        offsetY = (h - totalBoardHeight) / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Fondo global (pantalla)
        canvas.drawColor(Color.parseColor("#071025"))

        // Fondo y borde del tablero (visibles)
        val boardRect = RectF(offsetX, offsetY, offsetX + cellSize * game.cols, offsetY + cellSize * game.rows)
        val boardBg = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#0F2433") }
        canvas.drawRoundRect(boardRect, 12f, 12f, boardBg)

        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = maxOf(4f, cellSize * 0.05f)
            color = Color.parseColor("#00FF9C")
        }
        canvas.drawRoundRect(boardRect, 12f, 12f, borderPaint)

        // Dibujar celdas
        for (r in 0 until game.rows) {
            for (c in 0 until game.cols) {
                val value = game.board[r][c]
                val left = offsetX + c * cellSize
                val top = offsetY + r * cellSize
                val right = left + cellSize
                val bottom = top + cellSize

                when (value) {
                    1 -> { // Snake
                        paint.color = Color.parseColor("#00FF7A")
                        canvas.drawRoundRect(RectF(left + 2f, top + 2f, right - 2f, bottom - 2f), 6f, 6f, paint)
                    }
                    2 -> { // Apple
                        paint.color = Color.parseColor("#FF2E2E")
                        canvas.drawOval(RectF(left + cellSize*0.12f, top + cellSize*0.12f, right - cellSize*0.12f, bottom - cellSize*0.12f), paint)
                        // brillo
                        val shine = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE; alpha = 140 }
                        canvas.drawCircle(left + cellSize*0.72f, top + cellSize*0.28f, cellSize*0.06f, shine)
                    }
                    else -> { // Celda vacía (ligeramente distinta al fondo para marcar límites)
                        paint.color = Color.parseColor("#0B1A2A")
                        canvas.drawRoundRect(RectF(left + 1f, top + 1f, right - 1f, bottom - 1f), 4f, 4f, paint)
                    }
                }
            }
        }
    }

    override fun run() {
        game.move(direction)
        onScoreUpdate?.invoke(game.score)
        invalidate()

        if (game.isGameOver) {
            handler.removeCallbacks(this)
            onGameOver?.invoke(game.score)
        } else {
            handler.postDelayed(this, 250)
        }
    }

    fun setDirection(newDir: Direction) {
        if ((direction == Direction.UP && newDir == Direction.DOWN) ||
            (direction == Direction.DOWN && newDir == Direction.UP) ||
            (direction == Direction.LEFT && newDir == Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDir == Direction.LEFT)) {
            return
        }
        direction = newDir
    }
}