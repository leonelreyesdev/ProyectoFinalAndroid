package com.example.proyectofinalandroid.Memory

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.R

class MemoryGameActivity : AppCompatActivity() {
    private lateinit var grid: GridLayout
    private lateinit var scoreText: TextView
    private val handler = Handler()
    private var cards = mutableListOf<Card>()
    private var buttons = mutableListOf<Button>()
    private var flippedCards = mutableListOf<Int>()
    private var score = 0
    private val symbols = listOf("🍎", "🍌", "🍇", "🍓", "🍍", "🥝")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        grid = findViewById(R.id.gridLayout)
        scoreText = findViewById(R.id.tvScore)

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            setupGame()
        }


        setupGame()
    }

    private fun setupGame() {
        score = 0
        scoreText.text = "Puntos: $score"
        cards.clear()
        buttons.clear()
        flippedCards.clear()
        grid.removeAllViews()

        val allSymbols = (symbols + symbols).shuffled()
        for (i in allSymbols.indices) {
            cards.add(Card(i, allSymbols[i]))
        }

        for (i in cards.indices) {
            val button = Button(this).apply {
                text = "❓"
                textSize = 24f
                setOnClickListener { onCardClicked(i) }
            }
            buttons.add(button)
            grid.addView(button)
        }
    }

    private fun onCardClicked(index: Int) {
        val card = cards[index]
        if (card.isMatched || card.isFlipped || flippedCards.size == 2) return

        card.isFlipped = true
        buttons[index].text = card.symbol
        flippedCards.add(index)

        if (flippedCards.size == 2) {
            val first = cards[flippedCards[0]]
            val second = cards[flippedCards[1]]
            if (first.symbol == second.symbol) {
                first.isMatched = true
                second.isMatched = true
                score++
                scoreText.text = "Puntos: $score"
                flippedCards.clear()
                if (cards.all { it.isMatched }) {
                    scoreText.text = "¡Ganaste con $score puntos!"
                }
            } else {
                handler.postDelayed({
                    first.isFlipped = false
                    second.isFlipped = false
                    buttons[flippedCards[0]].text = "❓"
                    buttons[flippedCards[1]].text = "❓"
                    flippedCards.clear()
                }, 1000)
            }
        }
    }
}