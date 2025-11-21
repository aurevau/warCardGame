package com.example.warcardgame

import kotlin.collections.mutableListOf
import kotlin.random.Random

class Deck() {
    private val deck = mutableListOf<Card>()
    lateinit var currentCard : Card
    private val usedCards = mutableListOf<Card>()

    private lateinit var shuffledDeck: MutableList<Card>


    fun initializeCards() {
        deck.add(Card("clover", 1, R.drawable.clover_1))
        deck.add(Card("clover", 2, R.drawable.clover_2))
        deck.add(Card("clover", 3, R.drawable.clover_3))
        deck.add(Card("clover", 4, R.drawable.clover_4))
        deck.add(Card("clover", 5, R.drawable.clover_5))
        deck.add(Card("clover", 6, R.drawable.clover_6))
        deck.add(Card("clover", 7, R.drawable.clover_7))
        deck.add(Card("clover", 8, R.drawable.clover_8))
        deck.add(Card("clover", 9, R.drawable.clover_9))
        deck.add(Card("clover", 10, R.drawable.clover_10))
        deck.add(Card("clover", 11, R.drawable.clover_11))
        deck.add(Card("clover", 12, R.drawable.clover_12))
        deck.add(Card("clover", 13, R.drawable.clover_13))


        deck.add(Card("heart", 14, R.drawable.heart_1))
        deck.add(Card("heart", 15, R.drawable.heart_2))
        deck.add(Card("heart", 16, R.drawable.heart_3))
        deck.add(Card("heart", 17, R.drawable.heart_4))
        deck.add(Card("heart", 18, R.drawable.heart_5))
        deck.add(Card("heart", 19, R.drawable.heart_6))
        deck.add(Card("heart", 20, R.drawable.heart_7))
        deck.add(Card("heart", 21, R.drawable.heart_8))
        deck.add(Card("heart", 22, R.drawable.heart_9))
        deck.add(Card("heart", 23, R.drawable.heart_10))
        deck.add(Card("heart", 24, R.drawable.heart_11))
        deck.add(Card("heart", 25, R.drawable.heart_12))
        deck.add(Card("heart", 26, R.drawable.heart_13))


        deck.add(Card("diamond", 27, R.drawable.diamond_1))
        deck.add(Card("diamond", 28, R.drawable.diamond_2))
        deck.add(Card("diamond", 29, R.drawable.diamond_3))
        deck.add(Card("diamond", 30, R.drawable.diamond_4))
        deck.add(Card("diamond", 31, R.drawable.diamond_5))
        deck.add(Card("diamond", 32, R.drawable.diamond_6))
        deck.add(Card("diamond", 33, R.drawable.diamond_7))
        deck.add(Card("diamond", 34, R.drawable.diamond_8))
        deck.add(Card("diamond", 35, R.drawable.diamond_9))
        deck.add(Card("diamond", 36, R.drawable.diamond_10))
        deck.add(Card("diamond", 37, R.drawable.diamond_11))
        deck.add(Card("diamond", 38, R.drawable.diamond_12))
        deck.add(Card("diamond", 39, R.drawable.diamond_13))

        deck.add(Card("spade", 40, R.drawable.spade_1))
        deck.add(Card("spade", 41, R.drawable.spade_2))
        deck.add(Card("spade", 42, R.drawable.spade_3))
        deck.add(Card("spade", 43, R.drawable.spade_4))
        deck.add(Card("spade", 44, R.drawable.spade_5))
        deck.add(Card("spade", 45, R.drawable.spade_6))
        deck.add(Card("spade", 46, R.drawable.spade_7))
        deck.add(Card("spade", 47, R.drawable.spade_8))
        deck.add(Card("spade", 48, R.drawable.spade_9))
        deck.add(Card("spade", 49, R.drawable.spade_10))
        deck.add(Card("spade", 50, R.drawable.spade_11))
        deck.add(Card("spade", 51, R.drawable.spade_12))
        deck.add(Card("spade", 52, R.drawable.spade_13))

        shuffledDeck = deck.shuffled().toMutableList()
    }




    fun generateCard(): Card {
        if (shuffledDeck.isEmpty()) {
            throw Exception("No Cards left")
        }

        currentCard = shuffledDeck.removeAt(0)
        return currentCard
    }
}