package com.example.warcardgame

data class Player(var name: String) {
    val hand: MutableList<Card> = mutableListOf()
}