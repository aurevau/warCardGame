package com.example.warcardgame

import android.widget.TextView

class Game {
    val warPot : MutableList<Card> = mutableListOf()
    val deck = Deck()
    val players = mutableListOf<Player>()

    fun addPlayer(player: Player){
        players.add(player)

    }



    fun checkWin(player1: Player, player2: Player, card1 : Card, card2: Card): RoundResult {
        return when {

            card1.value == card2.value -> {
                player1.hand.add(card1)
                player2.hand.add(card2)
                RoundResult.TIE
            }
            card1.value == 15 -> {
                val stolenCards = listOfNotNull(
                    drawCard(player2),
                    drawCard(player2),
                    drawCard(player2),
                    drawCard(player2),
                    drawCard(player2))
                player1.hand.add(card1)
                player1.hand.add(card2)
                player1.hand.addAll(stolenCards)
                RoundResult.JOKERP1

            }

            card2.value == 15 -> {
                val stolenCards = listOfNotNull(
                    drawCard(player1),
                    drawCard(player1),
                    drawCard(player1),
                    drawCard(player1),
                    drawCard(player1))
                player2.hand.add(card1)
                player2.hand.add(card2)
                player2.hand.addAll(stolenCards)
                RoundResult.JOKERP2
            }

            card1.value > card2.value -> {
                player1.hand.add(card1)
                player1.hand.add(card2)
                RoundResult.PLAYER1_WIN
            }

            else -> {
                    player2.hand.add(card1)
                    player2.hand.add(card2)
                    RoundResult.PLAYER2_WIN
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