package com.example.warcardgame

import android.widget.TextView

class Game {
    val deck = Deck()
    val players = mutableListOf<Player>()

    fun addPlayer(player: Player){
        players.add(player)

    }

    fun checkWin(player1: Player, player2: Player, card1 : Card, card2: Card): String{
            return when{
                card1.value > card2.value -> {
                    player1.hand.add(card1)
                    player1.hand.add(card2)
                    "${player1.name} wins round!"

                }
                card2.value > card1.value -> {
                    player2.hand.add(card1)
                    player2.hand.add(card2)
                    "${player2.name} wins round!"

                }
                else -> {
                    player1.hand.add(card1)
                    player2.hand.add(card2)
                    "its a tie..."
                }
            }

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