package com.example.proyectofinalandroid.Snake

class SnakeGame(val rows: Int, val cols: Int, val winAppleCount: Int = 5) {
    val board = Array(rows) { IntArray(cols) }    // 0 empty, 1 snake, 2 apple
    private val snake = mutableListOf<Pair<Int, Int>>()
    private var apple = Pair(0, 0)
    var isGameOver = false
    var score = 0
    private var applesEaten = 0

    val snakeHead: Pair<Int, Int>
        get() = snake.first()

    init {
        reset()
    }

    fun reset() {
        isGameOver = false
        score = 0
        applesEaten = 0
        for (r in 0 until rows) for (c in 0 until cols) board[r][c] = 0
        val startR = rows / 2
        val startC = cols / 2
        snake.clear()
        snake.add(Pair(startR, startC))
        board[startR][startC] = 1
        placeApple()
    }

    fun move(dir: Direction) {
        if (isGameOver) return
        val head = snake.first()
        val newHead = when (dir) {
            Direction.UP -> Pair(head.first - 1, head.second)
            Direction.DOWN -> Pair(head.first + 1, head.second)
            Direction.LEFT -> Pair(head.first, head.second - 1)
            Direction.RIGHT -> Pair(head.first, head.second + 1)
        }

        if (newHead.first !in 0 until rows || newHead.second !in 0 until cols || board[newHead.first][newHead.second] == 1) {
            isGameOver = true
            return
        }

        snake.add(0, newHead)
        board[newHead.first][newHead.second] = 1

        if (newHead == apple) {
            score += 10
            applesEaten++
            placeApple()
        } else {
            val tail = snake.removeLast()
            board[tail.first][tail.second] = 0
        }
    }

    private fun placeApple() {
        // limpiar apple anterior
        for (r in 0 until rows) for (c in 0 until cols) if (board[r][c] == 2) board[r][c] = 0
        var tries = 0
        do {
            apple = Pair((0 until rows).random(), (0 until cols).random())
            tries++
            if (tries > rows * cols * 2) break
        } while (board[apple.first][apple.second] != 0)
        // si no hay espacio, no coloca apple y consideramos victoria
        if (board[apple.first][apple.second] == 0) {
            board[apple.first][apple.second] = 2
        }
    }

    fun hasWon(): Boolean {
        return applesEaten >= winAppleCount || snake.size >= rows * cols
    }

    fun getApplesEaten(): Int = applesEaten
}