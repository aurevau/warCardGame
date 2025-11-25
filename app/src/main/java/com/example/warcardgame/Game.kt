package com.example.warcardgame

class Game {
    val deck = Deck()
    val players = mutableListOf<Player>()

    fun addPlayer(player: Player){
        players.add(player)

    }

    fun start(){
        deck.initializeCards()
        deck.dealCards(players[0], players[1])
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