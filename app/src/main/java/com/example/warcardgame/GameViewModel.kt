package com.example.warcardgame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.logging.Handler

class GameViewModel: ViewModel() {

    private val warPlayerCardsSavedList = mutableListOf<Card?>()
    private val opponentCardsSavedList = mutableListOf<Card?>()
    private val game = Game()
    private lateinit var player1: Player
    private lateinit var player2: Player

    private val _player1Name = MutableLiveData<String>()
    val player1Name:  LiveData<String> = _player1Name

    private val _player2Name = MutableLiveData<String>()
    val player2Name:  LiveData<String> = _player2Name

    private val _player1Score = MutableLiveData<Int>()
    val player1Score: LiveData<Int> = _player1Score

    private val _player2Score = MutableLiveData<Int>()
    val player2Score: LiveData<Int> = _player2Score

    private val _playerCardImage = MutableLiveData<Int>()
    val playerCardImage: LiveData<Int> = _playerCardImage

    private val _opponentCardImage = MutableLiveData<Int>()
    val opponentCardImage: LiveData<Int> = _opponentCardImage

    private val _announcement = MutableLiveData<String>()
    val announcement: LiveData<String> = _announcement

    private val _navigateToWar = MutableLiveData<Boolean>()
    val navigateToWar: LiveData<Boolean> = _navigateToWar

    private val _navigateToPlay = MutableLiveData<Boolean>()
    val navigateToPlay: LiveData<Boolean> = _navigateToPlay

   private val _warPlayerCards = MutableLiveData<List<Int>>()
   val warPlayerCards: LiveData<List<Int>> = _warPlayerCards

   private val _warOpponentCards = MutableLiveData<List<Int>>()
   val warOpponentCards: LiveData<List<Int>> = _warOpponentCards

   private val _navigateToWinner = MutableLiveData<Boolean>()
   val navigateToWinner: LiveData<Boolean> = _navigateToWinner

    private val _winnerName = MutableLiveData<String>()
    val winnerName: LiveData<String> = _winnerName

    fun startGame(username: String) {
        player1 = Player(username)
        player2 = Player("CPU")

        game.addPlayer(player1)
        game.addPlayer(player2)
        game.start()

        _player1Name.value = player1.name
        _player2Name.value = player2.name

        updateScore()
    }

    fun dealCard(){
        val card1 = game.drawCard(player1)
        val card2 = game.drawCard(player2)

        _playerCardImage.value = card1?.image
        _opponentCardImage.value = card2?.image

        if (card1 != null && card2 != null){
           val result = game.checkWin(player1, player2, card1, card2)
            _announcement.value = result
        } else {
            _announcement.value = "Out of cards..."
        }

        if(card1?.value == card2?.value){
            _navigateToWar.value = true
        }

        if (game.isGameOver()){
            val winner = if(player1.hand.isEmpty()) player2.name else player1.name
            _winnerName.value = winner
            _announcement.value = "Winner is ${winner}"
            updateScore()
            _navigateToWinner.value = true


        }

        updateScore()

    }

    fun onWar(){

        if(player1.hand.size <3 || player2.hand.size <3) {
            _announcement.value = "Inte tillräckligt med kort för Krig!"
            _navigateToPlay.value = true
            return
        }
        val playerCard1 = game.drawCard(player1)
        val playerCard2 = game.drawCard(player1)
        val playerCard3 =  game.drawCard(player1)

        val opponentCard1 =  game.drawCard(player2)
        val opponentCard2 = game.drawCard(player2)
        val opponentCard3 = game.drawCard(player2)

        _warPlayerCards.value = listOf(R.drawable.background_card, R.drawable.background_card, R.drawable.background_card)
        _warOpponentCards.value = listOf(R.drawable.background_card, R.drawable.background_card, R.drawable.background_card)

        warPlayerCardsSavedList.clear()
        opponentCardsSavedList.clear()

        playerCard1?.let {warPlayerCardsSavedList.add(it)}
        playerCard2?.let {warPlayerCardsSavedList.add(it)}
        playerCard3?.let {warPlayerCardsSavedList.add(it)}

        opponentCard1?.let {opponentCardsSavedList.add(it)}
        opponentCard2?.let {opponentCardsSavedList.add(it)}
        opponentCard3?.let {opponentCardsSavedList.add(it)}
    }


    fun revealCard(index: Int){
        val opponentIndex =index + 3
        val playerCard = warPlayerCardsSavedList[index]
        val opponentCard = opponentCardsSavedList[index]
        game.warPot.clear()
        game.warPot.addAll((warPlayerCardsSavedList + opponentCardsSavedList).filterNotNull())
        _warPlayerCards.value = listOf(
            if (index == 0) playerCard?.image ?: R.drawable.background_card else R.drawable.background_card,
            if (index == 1) playerCard?.image ?: R.drawable.background_card else R.drawable.background_card,
            if (index == 2) playerCard?.image ?: R.drawable.background_card else R.drawable.background_card
        )
        _warOpponentCards.value = listOf(
            if (opponentIndex == 3) opponentCard?.image ?: R.drawable.background_card else R.drawable.background_card,
            if (opponentIndex == 4) opponentCard?.image ?: R.drawable.background_card else R.drawable.background_card,
            if (opponentIndex == 5) opponentCard?.image ?: R.drawable.background_card else R.drawable.background_card
        )

        if (playerCard != null && opponentCard != null){
            val winner = when {
                playerCard.value > opponentCard.value -> player1
                opponentCard.value > playerCard.value -> player2
                else -> null // tie
            }

            if(winner != null){
                winner.hand.addAll(game.warPot)
                game.warPot.clear()
                _announcement.value = "${winner.name} wins war"
                _navigateToPlay.value = true
            } else {
                _announcement.value = "Tie, open next card!"
            }
        }

        updateScore()
    }

    fun updateScore(){
        _player1Score.value = player1.hand.size
        _player2Score.value = player2.hand.size
    }

    fun doneNavigatingToWinner(){
        _navigateToWinner.value = null
    }

    fun doneNavigatingToPlay(){
        _navigateToPlay.value = null
    }

    fun doneNavigatingToWar(){
        _navigateToWar.value = null
    }



}

