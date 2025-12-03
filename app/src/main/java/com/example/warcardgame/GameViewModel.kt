package com.example.warcardgame

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class GameViewModel(application: Application): AndroidViewModel(application) {

    private val warPlayerCardsSavedList = mutableListOf<Card?>()
    private val opponentCardsSavedList = mutableListOf<Card?>()
    private val game = Game()
    val deck = Deck()
    private lateinit var savedUserName : String

    private lateinit var player1: Player
    private lateinit var player2: Player

    private val _firstRound = MutableLiveData<Boolean>()
    val firstRound: LiveData<Boolean> = _firstRound

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

    private val _warAnnouncement = MutableLiveData<String>()
    val warAnnouncement: LiveData<String> = _warAnnouncement
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

    private val _roundWinnerName = MutableLiveData<String>()
    val roundWinnerName: LiveData<String> = _roundWinnerName

    private val _finalWinnerName = MutableLiveData<String>()
    val finalWinnerName: LiveData<String> = _finalWinnerName

    private val _warWinnerName = MutableLiveData<String>()
    val warWinnerName: LiveData<String> = _warWinnerName


    fun startGame(username: String) {
        _firstRound.value = true
        savedUserName = username
        player1 = Player(username)
        player2 = Player(getApplication<Application>().getString(R.string.cpu))



        game.addPlayer(player1)
        game.addPlayer(player2)

        deck.initializeCards()
        deck.dealCards(player1, player2)


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

            if (result == RoundResult.TIE){
                _roundWinnerName.value = "tie"
                _navigateToWar.value = true

            } else {
                _roundWinnerName.value = when (result) {
                    RoundResult.PLAYER1_WIN -> player1.name
                    RoundResult.PLAYER2_WIN -> player2.name
                    else -> ""
                }
            }
        } else {
            _announcement.value = "noCards"

        }

        if (game.isGameOver()){
            val winner = if(player1.hand.isEmpty()) player2.name else player1.name
            _finalWinnerName.value = winner
            updateScore()
            _navigateToWinner.value = true


        }

        updateScore()

    }

    fun onWar(){

        if(player1.hand.size <3 || player2.hand.size <3) {
            _warAnnouncement.value = "noWarCards"
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
            if (index == 0) opponentCard?.image ?: R.drawable.background_card else R.drawable.background_card,
            if (index == 1) opponentCard?.image ?: R.drawable.background_card else R.drawable.background_card,
            if (index == 2) opponentCard?.image ?: R.drawable.background_card else R.drawable.background_card
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
                _warWinnerName.value = winner.name

                _navigateToPlay.value = true
//                _announcement.value = ""
            } else {
               _warWinnerName.value = "tie"
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

    fun resetGame(){
        _firstRound.value = false
        game.clearPlayers()

        player1 = Player(savedUserName)
        player2 = Player("CPU")

        game.addPlayer(player1)
        game.addPlayer(player2)

        deck.resetDeck()
        deck.dealCards(player1, player2)

        _player1Name.value = player1.name
        _player2Name.value = player2.name



        updateScore()

        _playerCardImage.value = R.drawable.background_card
        _opponentCardImage.value = R.drawable.background_card
        resetRoundWinner()
        _finalWinnerName.value= null





    }

    fun resetStartFragment(username: String){
        _firstRound.value = false
        game.clearPlayers()

        player1 = Player(username)
        player2 = Player("CPU")
        deck.resetDeck()
        deck.dealCards(player1, player2)

        _player1Name.value = player1.name
        _player2Name.value = player2.name

        updateScore()

        _playerCardImage.value = R.drawable.background_card
        _opponentCardImage.value = R.drawable.background_card

        _finalWinnerName.value= null
        _announcement.value = ""
        resetRoundWinner()
    }

    fun resetRoundWinner() {
        _roundWinnerName.value = null
    }



}

