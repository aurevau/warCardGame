package com.example.warcardgame

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import com.example.warcardgame.databinding.ActivityMainBinding
import java.util.logging.Handler

class MainActivity : AppCompatActivity(), StartFragment.StartFragmentListener, PlayFragment.PlayFragmentListener, WarFragment.WarFragmentListener {
    lateinit var binding: ActivityMainBinding

    val startFragment = StartFragment()
    val playFragment = PlayFragment()

    val winnerFragment = WinnerFragment()


    lateinit var player1: Player
    lateinit var player2: Player

    lateinit var game: Game




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.mainContainer.id, startFragment)
        transaction.commit()


    }


    override fun dealButtonClicked(
        cardPlayer: ImageView,
        cardCPU: ImageView
    ): String {
        val playerCard = game.drawCard(player1)
        val cpuCard = game.drawCard(player2)

        playerCard?.let { cardPlayer.setImageResource(it.image) }
        cpuCard?.let { cardCPU.setImageResource(it.image) }




        if (game.isGameOver()) {
            val winner = when {
                player1.hand.isEmpty() -> player2.name
                player2.hand.isEmpty() -> player1.name
                else -> "its a tie"

            }
            val dealButton = findViewById<ImageButton>(R.id.deal_btn)
            dealButton.isEnabled = false
            setScore()
            openWinnerFragment(winner)
            return "GAME OVER! \n Winner is $winner"
        }

        return if (playerCard != null && cpuCard != null) {
            val roundResult = game.checkWin(player1, player2, playerCard, cpuCard)
            setScore()
            if (roundResult == "its a tie...") {

                val dealButton = findViewById<ImageButton>(R.id.deal_btn)
                dealButton.isEnabled = false
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    startWar()

                }, 1000)

                return "It'a tie! Time for war..."
            } else {

                roundResult
            }
        } else {
            "no more cards"
        }

    }


    override fun exitButtonClicked() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, startFragment)
            .commit()
    }


    override fun getScores(): Pair<Int, Int> {
    return Pair(player1.hand.size, player2.hand.size)    }


    override fun usernameButtonClicked(username: String) {
        game = Game()
        player1 = Player(username)
        player2 = Player("CPU")
        game.addPlayer(player1)
        game.addPlayer(player2)
        game.start()

        openPlayFragment(player1.name, player2.name)


    }


    private fun openPlayFragment(player1Name: String, player2Name: String) {
        supportFragmentManager.beginTransaction().apply {
            val bundle = Bundle()
            bundle.putString("player1_name", player1Name)
            bundle.putString("player2_name", player2Name)
            playFragment.arguments = bundle
            replace(binding.mainContainer.id, playFragment)
            commit()
        }
    }


     fun setScore(){
        player1.score = player1.hand.size
        player2.score = player2.hand.size



    }

    fun startWar() {
        supportFragmentManager.beginTransaction().apply {
            val warFragment = WarFragment()
            replace(binding.mainContainer.id, warFragment)
            addToBackStack(null)
            commit()
        }
    }


    override fun onWar(
        playerCard1: ImageView,
        playerCard2: ImageView,
        playerCard3: ImageView,
        opponentCard1: ImageView,
        opponentCard2: ImageView,
        opponentCard3: ImageView
    ) {



        repeat(3) {
            game.drawCard(player1)?.let { game.warPot.add(it) }
            game.drawCard(player2)?.let { game.warPot.add(it) }
        }




        val playerCardViews = listOf(playerCard1, playerCard2, playerCard3)
        val opponentCardViews = listOf(opponentCard1, opponentCard2, opponentCard3)

        playerCard1.setOnClickListener {
            revealCard(0, playerCard1, opponentCardViews)
        }
        playerCard2.setOnClickListener {
            revealCard(1, playerCard2, opponentCardViews)
        }

        playerCard3.setOnClickListener {
            revealCard(2, playerCard3, opponentCardViews)
        }

    }

    fun revealCard(index: Int, playerCardView: ImageView, opponentCardViews: List<ImageView>) {
        val tieOffset = 0
        val playerCard = game.warPot[tieOffset + index]
        val opponentCard = game.warPot[tieOffset + index + 3]

        playerCardView.setImageResource(playerCard.image)
        opponentCardViews[index].setImageResource(
            opponentCard.image)


        val playerValue = playerCard.value
        val opponentValue = opponentCard.value
        val result = when {
            playerValue > opponentValue -> {
                "${player1.name} wins war!"
            }
            opponentValue > playerValue -> {
                "${player2.name} wins war!"
            }
            else -> {

                "WAR AGAIN"
            }
        }

        findViewById<TextView>(R.id.textViewWar).text = result


        if (result.contains("wins war")) {
            givePotToWinner((if(playerCard.value > opponentCard.value) player1 else player2))
            setScore()

            android.os.Handler(Looper.getMainLooper()).postDelayed({
                supportFragmentManager.beginTransaction()
                    .replace(binding.mainContainer.id, playFragment)
                    .commitAllowingStateLoss()
            }, 1000)

        }

    }

    fun openWinnerFragment(winner: String){
        supportFragmentManager.beginTransaction().apply{
            val bundle = Bundle()
            bundle.putString("winner", winner)
            winnerFragment.arguments = bundle
            replace(binding.mainContainer.id, winnerFragment)
            commit()
        }
    }

    fun givePotToWinner(winner: Player){
        winner.hand.addAll(game.warPot)

        game.warPot.clear()
    }


}