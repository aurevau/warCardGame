package com.example.warcardgame

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class GameViewModel(application: Application): AndroidViewModel(application) {
    private val warPlayerCardsSavedList = mutableListOf<Card?>()
    private val opponentCardsSavedList = mutableListOf<Card?>()
    private val game = Game()
    val deck = Deck()
    private lateinit var savedUserName: String

    private lateinit var player1: Player
    private lateinit var player2: Player

    private val _firstRound = MutableLiveData<Boolean>()
    val firstRound: LiveData<Boolean> = _firstRound

    private val _player1Name = MutableLiveData<String>()
    val player1Name: LiveData<String> = _player1Name

    private val _player2Name = MutableLiveData<String>()
    val player2Name: LiveData<String> = _player2Name

    private val _player1Score = MutableLiveData<Int>()
    val player1Score: LiveData<Int> = _player1Score

    private val _player2Score = MutableLiveData<Int>()
    val player2Score: LiveData<Int> = _player2Score

    private val _playerCardImage = MutableLiveData<Int>()
    val playerCardImage: LiveData<Int> = _playerCardImage

    private val _opponentCardImage = MutableLiveData<Int>()
    val opponentCardImage: LiveData<Int> = _opponentCardImage

    private val _warAnnouncement = MutableLiveData<String>()
    val warAnnouncement: LiveData<String> = _warAnnouncement
    private val _navigateToWar = MutableLiveData<Boolean>()
    val navigateToWar: LiveData<Boolean> = _navigateToWar

    private val _navigateToPlay = MutableLiveData<Boolean>()
    val navigateToPlay: LiveData<Boolean> = _navigateToPlay

    private val _navigateToWinner = MutableLiveData<Boolean>()
    val navigateToWinner: LiveData<Boolean> = _navigateToWinner

    private val _warPlayerCards = MutableLiveData<List<Int>>()
    val warPlayerCards: LiveData<List<Int>> = _warPlayerCards

    private val _warOpponentCards = MutableLiveData<List<Int>>()
    val warOpponentCards: LiveData<List<Int>> = _warOpponentCards

    private val _roundWinnerName = MutableLiveData<String>()
    val roundWinnerName: LiveData<String> = _roundWinnerName

    private val _finalWinnerName = MutableLiveData<String>()
    val finalWinnerName: LiveData<String> = _finalWinnerName

    private val _warWinnerName = MutableLiveData<String>()
    val warWinnerName: LiveData<String> = _warWinnerName

    private val _cardsDisabled = MutableLiveData<Boolean>()
    val cardsDisabled: LiveData<Boolean> = _cardsDisabled

    private val _jokerListener = MutableLiveData<String>()
    val jokerListener: LiveData<String> = _jokerListener

    fun startGame(username: String) {
        _firstRound.value = true
        savedUserName = username
        player1 = Player(username)
        player2 = Player(getApplication<Application>().getString(R.string.cpu))

        game.addPlayer(player1)
        game.addPlayer(player2)

        deck.initializeCards()
        deck.dealCards(player1, player2)

        updateNames()
        updateScore()
    }

    fun dealCard() {
        val card1 = game.drawCard(player1)
        val card2 = game.drawCard(player2)
        _playerCardImage.value = card1?.image
        _opponentCardImage.value = card2?.image

        if (card1 != null && card2 != null) {
            val result = game.checkWin(player1, player2, card1, card2)
            handleRoundResult(result)
        }

        checkEndGame()
        updateScore()
    }

    fun onWar() {
        _cardsDisabled.value = false
        checkCardsForWar()

        val playerCard1 = game.drawCard(player1)
        val playerCard2 = game.drawCard(player1)
        val playerCard3 = game.drawCard(player1)

        val opponentCard1 = game.drawCard(player2)
        val opponentCard2 = game.drawCard(player2)
        val opponentCard3 = game.drawCard(player2)

        setWarCardsBackground()
        clearWarLists()

        playerCard1?.let { warPlayerCardsSavedList.add(it) }
        playerCard2?.let { warPlayerCardsSavedList.add(it) }
        playerCard3?.let { warPlayerCardsSavedList.add(it) }

        opponentCard1?.let { opponentCardsSavedList.add(it) }
        opponentCard2?.let { opponentCardsSavedList.add(it) }
        opponentCard3?.let { opponentCardsSavedList.add(it) }
    }


    fun revealCard(index: Int) {
        val opponentIndex = (0..2).random()
        val playerCard = warPlayerCardsSavedList[index]
        val opponentCard = opponentCardsSavedList[opponentIndex]
        fillWarPot()

        // Skapar kopia av livedatalistan för att kunna ta ut drawable int
        val currentPlayerCards = _warPlayerCards.value?.toMutableList() ?: MutableList(3) { R.drawable.background_card }
        val currentOpponentCards = _warOpponentCards.value?.toMutableList()
            ?: MutableList(3) { R.drawable.background_card }

        currentPlayerCards[index] = playerCard?.image ?: R.drawable.background_card
        currentOpponentCards[opponentIndex] = opponentCard?.image ?: R.drawable.background_card

        _warPlayerCards.value = currentPlayerCards
        _warOpponentCards.value = currentOpponentCards

        checkJoker(playerCard, opponentCard)
        val winner = checkWinner(playerCard, opponentCard)
        handleWarWinner(winner)
        checkEndGame()
        updateScore()
    }

    fun handleTie(){
        _roundWinnerName.value = "tie"
        if (player1.hand.size < 3 || player2.hand.size < 3) {
            _roundWinnerName.value = "noWarCards"
            _navigateToWar.value = false
        } else {
            _navigateToWar.value = true
            _warAnnouncement.value = null

        }
    }

    fun handleRoundResult(result: RoundResult){
        when (result) {
            RoundResult.PLAYER1_WIN -> _roundWinnerName.value = player1.name
            RoundResult.PLAYER2_WIN -> _roundWinnerName.value = player2.name
            RoundResult.TIE -> handleTie()
            RoundResult.JOKERP1 -> _jokerListener.value = player1.name
            RoundResult.JOKERP2 -> _jokerListener.value = player2.name
        }
    }

    fun updateScore() {
        _player1Score.value = player1.hand.size
        _player2Score.value = player2.hand.size
    }

    fun doneNavigatingToWinner() {
        _navigateToWinner.value = null
    }

    fun doneNavigatingToPlay() {
        _navigateToPlay.value = null
    }

    fun doneNavigatingToWar() {
        _navigateToWar.value = null
    }

    fun resetGame() {
        _firstRound.value = false
        game.clearPlayers()
        game.warPot.clear()

        warPlayerCardsSavedList.clear()
        opponentCardsSavedList.clear()

        player1 = Player(savedUserName)
        player2 = Player("CPU")

        game.addPlayer(player1)
        game.addPlayer(player2)

        deck.resetDeck()
        deck.dealCards(player1, player2)

        updateNames()
        updateScore()
        setBackgroundCard()
        resetRoundWinner()
        resetFinalWinner()


    }

    fun updateNames(){
        _player1Name.value = player1.name
        _player2Name.value = player2.name
    }

    fun handleWarWinner(winner: Player?) {
        if (winner != null) {
            _cardsDisabled.value = true
            winner.hand.addAll(game.warPot)
            game.warPot.clear()
            _warWinnerName.value = winner.name
            _navigateToPlay.value = true
            _roundWinnerName.value = "back"

        } else {
            _cardsDisabled.value = false
            _warWinnerName.value = "tie"

        }
    }


    fun resetStartFragment(username: String) {
        _firstRound.value = false
        game.clearPlayers()

        player1 = Player(username)
        player2 = Player("CPU")
        deck.resetDeck()
        deck.dealCards(player1, player2)

        updateNames()
        updateScore()
        setBackgroundCard()
        resetFinalWinner()
        resetRoundWinner()
    }

    fun setBackgroundCard(){
        _playerCardImage.value = R.drawable.background_card
        _opponentCardImage.value = R.drawable.background_card
    }

    fun resetFinalWinner() {
        _finalWinnerName.value = null
    }

    fun resetRoundWinner() {
        _roundWinnerName.value = null
    }

    fun jokerHandled() {
        _jokerListener.value = null
    }

    fun checkWinner(playerCard: Card?, opponentCard: Card?): Player? {
        return when{
            playerCard == null || opponentCard == null -> null
            playerCard.value > opponentCard.value -> player1
            opponentCard.value > playerCard.value -> player2
            else -> null // tie
        }
    }

    fun checkEndGame(){
        if (game.isGameOver()) {
            val winner = if (player1.hand.isEmpty()) player2.name else player1.name
            _finalWinnerName.value = winner
            updateScore()
            _navigateToWinner.value = true

        }
    }

    fun fillWarPot(){
        game.warPot.clear()
        game.warPot.addAll((warPlayerCardsSavedList + opponentCardsSavedList).filterNotNull())
    }

    fun setWarCardsBackground(){
        _warPlayerCards.value = listOf(
            R.drawable.background_card,
            R.drawable.background_card,
            R.drawable.background_card
        )
        _warOpponentCards.value = listOf(
            R.drawable.background_card,
            R.drawable.background_card,
            R.drawable.background_card
        )
    }

    fun checkCardsForWar(){
        if (player1.hand.size < 3 || player2.hand.size < 3) {
            _warAnnouncement.value = "noWarCards"
            _navigateToPlay.value = true
            return
        }
    }

    fun clearWarLists(){
        warPlayerCardsSavedList.clear()
        opponentCardsSavedList.clear()
    }

    fun checkJoker(playerCard: Card?, opponentCard: Card?){
        if (playerCard?.value == 15) {
            _jokerListener.value = player1.name
            _cardsDisabled.value = true
            _navigateToPlay.value = true
            return
        } else if (opponentCard?.value == 15) {
            _jokerListener.value = player2.name
            _cardsDisabled.value = true
            _navigateToPlay.value = true
            return
        }
    }




}
