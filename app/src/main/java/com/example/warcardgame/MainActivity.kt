package com.example.warcardgame

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.warcardgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), StartFragment.StartFragmentListener, PlayFragment.PlayFragmentListener {
    lateinit var binding : ActivityMainBinding
    var startFragment = StartFragment()
    var playFragment = PlayFragment()
    lateinit var player1: Player
    lateinit var player2: Player

    val game = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.mainContainer.id, startFragment)
        transaction.commit()


    }

    override fun startButtonClicked(username: String) {
        player1 = Player(username)
        player2 = Player("CPU")
        game.addPlayer(player1)
        game.addPlayer(player2)
        game.start()

        supportFragmentManager.beginTransaction().apply{
            val bundle = Bundle()

            bundle.putString("username_key", "$username")
            playFragment.arguments = bundle
            replace(binding.mainContainer.id, playFragment)
                .commit()
        }
    }

    override fun dealButtonClicked(
        cardPlayer: ImageView,
        cardCPU: ImageView
    ) {
        val playerCard = game.drawCard(player1)
        val cpuCard = game.drawCard(player2)

        playerCard?.let { cardPlayer.setImageResource(it.image) }
        cpuCard?.let { cardCPU.setImageResource(it.image) }

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
                    findViewById<TextView>(R.id.tv_announcement).text = "CPU wins round!"
                }
                else -> {
                    player1.hand.add(playerCard)
                    player2.hand.add(cpuCard)
                    findViewById<TextView>(R.id.tv_announcement).text = "Oh, its a tie!!"
                }
            }
        }
    }
}