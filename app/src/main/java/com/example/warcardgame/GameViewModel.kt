package com.example.warcardgame

import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    private val game = Game()


    fun start(){
        game.start()
    }

    fun drawCard(player: Player): Card?{
        return game.drawCard(player)
    }

    fun isGameOver(): Boolean{
        return game.isGameOver()
    }

}