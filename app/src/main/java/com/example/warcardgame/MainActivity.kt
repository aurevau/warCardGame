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

class MainActivity : AppCompatActivity(), StartFragment.StartFragmentListener, PlayFragment.PlayFragmentListener {
    lateinit var binding : ActivityMainBinding
    private var playerNames = mutableListOf<String>()

    var startFragment = StartFragment()
    var playFragment = PlayFragment()
    var multiplayerFragment = MultiplayerFragment()

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
    ): String {
        val playerCard = game.drawCard(player1)
        val cpuCard = game.drawCard(player2)

        playerCard?.let { cardPlayer.setImageResource(it.image) }
        cpuCard?.let { cardCPU.setImageResource(it.image) }

        setScore()

        if (game.isGameOver()){
            val winner = when {
                player1.hand.isEmpty() -> player2.name
                player2.hand.isEmpty() -> player1.name
                else -> "its a tie"
            }
            return "GAME OVER! \n Winner is $winner"
        }

        return if (playerCard != null && cpuCard != null) {
            game.checkWin(player1, player2, playerCard, cpuCard)
        } else {
            "no more cards"
        }

    }


    override fun exitButtonClicked() {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, startFragment)
            .commit()    }


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
        supportFragmentManager.beginTransaction().apply{
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
        findViewById<TextView>(R.id.tv_player1_score).text = player1.score.toString()
        findViewById<TextView>(R.id.tv_player2_score).text = player2.score.toString()



    }
}