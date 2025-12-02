package com.example.warcardgame

import android.widget.TextView

class Game {
    val warPot : MutableList<Card> = mutableListOf()
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



    fun drawCard(player: Player): Card? {
        return if(player.hand.isNotEmpty()){
            player.hand.removeAt(0)
        } else null
    }


    fun isGameOver(): Boolean {
        return players.any {it.hand.isEmpty()}
    }

    fun clearPlayers(){
        players.clear()
    }



}