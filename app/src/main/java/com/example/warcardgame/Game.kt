package com.example.warcardgame

import android.widget.TextView

class Game {
    val deck = Deck()
    val players = mutableListOf<Player>()

    fun addPlayer(player: Player){
        players.add(player)

    }

    fun start(){
        deck.initializeCards()
        deck.dealCards(players[0], players[1])
        players[0].score = players[0].hand.size
        players[1].score = players[1].hand.size


    }

    fun drawCard(player: Player): Card? {
        return if(player.hand.isNotEmpty()){
            player.hand.removeAt(0)
        } else null
    }

    fun isGameOver(): Boolean {
        return players.all {it.hand.isEmpty()}
    }



}