package com.example.warcardgame

data class Player(var name: String) {
    val hand: MutableList<Card> = mutableListOf()
    var score : Int = 0

    val warHand : MutableList<Card> = mutableListOf()

    val currentScore: Int
        get() = hand.size
}