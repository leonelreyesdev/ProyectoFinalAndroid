package com.example.proyectofinalandroid.BreakOut

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalandroid.R

class BreakoutActivity : AppCompatActivity() {
    private lateinit var breakoutView: BreakoutView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_break_out)

        breakoutView = findViewById(R.id.breakoutView)

        findViewById<Button>(R.id.btnLeft).setOnClickListener {
            breakoutView.movePaddleLeft()
        }

        findViewById<Button>(R.id.btnRight).setOnClickListener {
            breakoutView.movePaddleRight()
        }

        findViewById<Button>(R.id.btnRestartBreakout).setOnClickListener {
            breakoutView.resetGame()
        }
    }
}