package com.example.warcardgame

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.warcardgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StartFragment.StartFragmentListener, PlayFragment.PlayFragmentListener, UsernameFragment.UsernameFragmentListener {
    lateinit var binding : ActivityMainBinding
    private var playerNames = mutableListOf<String>()

    var startFragment = StartFragment()
    var playFragment = PlayFragment()

    lateinit var player1: Player
    lateinit var player2: Player
    var totalPlayers : Int = 0

    lateinit var game : Game



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
    ) {
        val playerCard = game.drawCard(player1)
        val cpuCard = game.drawCard(player2)

        playerCard?.let { cardPlayer.setImageResource(it.image) }
        cpuCard?.let { cardCPU.setImageResource(it.image) }

        player1.score = player1.hand.size
        player2.score = player2.hand.size
        findViewById<TextView>(R.id.tv_player1_score).text = player1.score.toString()
        findViewById<TextView>(R.id.tv_player2_score).text = player2.score.toString()

        if(playerCard != null && cpuCard != null) {
            when{
                playerCard.value > cpuCard.value -> {
                    player1.hand.add(playerCard)
                    player1.hand.add(cpuCard)
                    findViewById<TextView>(R.id.tv_announcement).text = "${player1.name} wins round!"

                }
                cpuCard.value > playerCard.value -> {
                    player2.hand.add(playerCard)
                    player2.hand.add(cpuCard)
                    findViewById<TextView>(R.id.tv_announcement).text = "${player2.name} wins round!"

                }
                else -> {
                    player1.hand.add(playerCard)
                    player2.hand.add(cpuCard)

                }
            }
        }

        player1.score = player1.hand.size
        player2.score = player2.hand.size
        findViewById<TextView>(R.id.tv_player1_score).text = player1.score.toString()
        findViewById<TextView>(R.id.tv_player2_score).text = player2.score.toString()

        if(game.isGameOver()) {
            val loser = when {
                player1.hand.isEmpty() -> player1.name
                player2.hand.isEmpty() -> player2.name
                else -> "its a tie"
            }

            findViewById<TextView>(R.id.tv_announcement).text = "GAME OVER! Loser is: $loser"
        }
    }

    override fun exitButtonClicked() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, startFragment)
            .commit()    }


    override fun usernameButtonClicked(username: String) {
        game = Game()
        if (totalPlayers == 1) {
            player1 = Player(username)
            player2 = Player("CPU")
            game.addPlayer(player1)
            game.addPlayer(player2)
            game.start()

            openPlayFragment(player1.name, player2.name)
        } else if (totalPlayers == 2) {
            playerNames.add(username)
            if (playerNames.size == 2) {

                player1 = Player(playerNames[0])
                player2 = Player(playerNames[1])
                game.addPlayer(player1)
                game.addPlayer(player2)
                game.start()

                openPlayFragment(player1.name, player2.name)
                playerNames.clear()
            }
        }




    }



    private fun openPlayFragment(player1Name: String, player2Name: String) {
        supportFragmentManager.beginTransaction().apply{
            val bundle = Bundle()
            bundle.putString("player1_name", player1Name)
            bundle.putString("player2_name", player2Name)
            playFragment.arguments = bundle
            replace(binding.mainContainer.id, playFragment)
            commit()
        }
    }

    override fun onePlayerButtonClicked() {
        totalPlayers = 1
        val usernameFragment = UsernameFragment()
        val bundle = Bundle()
        bundle.putInt("totalPlayers", totalPlayers)
        usernameFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, usernameFragment)
            .commit()

    }

        override fun twoPlayersButtonClicked() {
            totalPlayers = 2
            val usernameFragment = UsernameFragment()
            val bundle = Bundle()
            bundle.putInt("totalPlayers", totalPlayers)
            usernameFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(binding.mainContainer.id, usernameFragment)
                .commit()


        }



}