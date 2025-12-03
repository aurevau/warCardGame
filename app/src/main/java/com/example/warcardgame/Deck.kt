package com.example.warcardgame

import kotlin.collections.mutableListOf
import kotlin.random.Random

class Deck() {
     val deck = mutableListOf<Card>()

    private lateinit var shuffledDeck: MutableList<Card>


    fun initializeCards() {
        deck.clear()
        deck.add(Card("clover", 14, R.drawable.clover_1))
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
        deck.add(Card("heart", 2, R.drawable.heart_2))
        deck.add(Card("heart", 3, R.drawable.heart_3))
        deck.add(Card("heart", 4, R.drawable.heart_4))
        deck.add(Card("heart", 5, R.drawable.heart_5))
        deck.add(Card("heart", 6, R.drawable.heart_6))
        deck.add(Card("heart", 7, R.drawable.heart_7))
        deck.add(Card("heart", 8, R.drawable.heart_8))
        deck.add(Card("heart", 9, R.drawable.heart_9))
        deck.add(Card("heart", 10, R.drawable.heart_10))
        deck.add(Card("heart", 11, R.drawable.heart_11))
        deck.add(Card("heart", 12, R.drawable.heart_12))
        deck.add(Card("heart", 13, R.drawable.heart_13))


        deck.add(Card("diamond", 14, R.drawable.diamond_1))
        deck.add(Card("diamond", 2, R.drawable.diamond_2))
        deck.add(Card("diamond", 3, R.drawable.diamond_3))
        deck.add(Card("diamond", 4, R.drawable.diamond_4))
        deck.add(Card("diamond", 5, R.drawable.diamond_5))
        deck.add(Card("diamond", 6, R.drawable.diamond_6))
        deck.add(Card("diamond", 7, R.drawable.diamond_7))
        deck.add(Card("diamond", 8, R.drawable.diamond_8))
        deck.add(Card("diamond", 9, R.drawable.diamond_9))
        deck.add(Card("diamond", 10, R.drawable.diamond_10))
        deck.add(Card("diamond", 11, R.drawable.diamond_11))
        deck.add(Card("diamond", 12, R.drawable.diamond_12))
        deck.add(Card("diamond", 13, R.drawable.diamond_13))

        deck.add(Card("spade", 14, R.drawable.spade_1))
        deck.add(Card("spade", 2, R.drawable.spade_2))
        deck.add(Card("spade", 3, R.drawable.spade_3))
        deck.add(Card("spade", 4, R.drawable.spade_4))
        deck.add(Card("spade", 5, R.drawable.spade_5))
        deck.add(Card("spade", 6, R.drawable.spade_6))
        deck.add(Card("spade", 7, R.drawable.spade_7))
        deck.add(Card("spade", 8, R.drawable.spade_8))
        deck.add(Card("spade", 9, R.drawable.spade_9))
        deck.add(Card("spade", 10, R.drawable.spade_10))
        deck.add(Card("spade", 11, R.drawable.spade_11))
        deck.add(Card("spade", 12, R.drawable.spade_12))
        deck.add(Card("spade", 13, R.drawable.spade_13))

        shuffledDeck = deck.shuffled().toMutableList()
    }




    fun dealCards(player1: Player, player2: Player) {
        player1.hand.clear()
        player2.hand.clear()
        var toP1 = true
        while (shuffledDeck.isNotEmpty()) {
            val card = shuffledDeck.removeAt(0)
            if (toP1) {
                player1.hand.add(card)
            } else {
                player2.hand.add(card)
            }
            toP1 = !toP1


        }


    }

    fun resetDeck(){
        shuffledDeck.clear()
        initializeCards()
    }
}