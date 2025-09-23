package com.example.proyectofinalandroid.Memory

data class Card(
    val id: Int,
    val symbol: String,
    var isMatched: Boolean = false,
    var isFlipped: Boolean = false
)
