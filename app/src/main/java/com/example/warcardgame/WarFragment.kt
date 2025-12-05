package com.example.warcardgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.warcardgame.databinding.FragmentWarBinding

class WarFragment : Fragment() {
    lateinit var binding: FragmentWarBinding
    lateinit var viewModel : GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.playerCard1.setOnClickListener {
            viewModel.revealCard(0)
        }

        binding.playerCard2.setOnClickListener {
            viewModel.revealCard(1)
        }

        binding.playerCard3.setOnClickListener {
            viewModel.revealCard(2)
        }

        viewModel.warPlayerCards.observe(viewLifecycleOwner){cards ->
            binding.playerCard1.setImageResource(cards[0])
            binding.playerCard2.setImageResource(cards[1])
            binding.playerCard3.setImageResource(cards[2])
        }

        viewModel.warOpponentCards.observe(viewLifecycleOwner){cards ->
            binding.opponentCard1.setImageResource(cards[0])
            binding.opponentCard2.setImageResource(cards[1])
            binding.opponentCard3.setImageResource(cards[2])
        }

        viewModel.navigateToPlay.observe(viewLifecycleOwner){backToPlay ->
            if(backToPlay == true){
                binding.root.postDelayed({
                    parentFragmentManager.popBackStack()
                }, 1500)

                viewModel.doneNavigatingToPlay()
            }

        }

        viewModel.warWinnerName.observe(viewLifecycleOwner) { name ->
            when (name) {
                "jokerp1" -> {
                    setTextView(getString(R.string.joker_war, viewModel.player1Name.value))
                }

                "jokerp2" -> {
                    setTextView(getString(R.string.joker_war, viewModel.player2Name.value))
                }

                "tie" -> {
                    setTextView(getString(R.string.warTie))
                }
                else -> {  setTextView(getString(R.string.winner_war, name))
                }
            }
        }
        viewModel.warAnnouncement.observe(viewLifecycleOwner){text ->
           when {
               text == null -> {
                   setTextView(getString(R.string.war_text))
               }
           }
    }

        viewModel.jokerListener.observe(viewLifecycleOwner){name ->
            name ?: return@observe
            yoyoOnAllWarCards()
//            setTextView(getString(R.string.joker_war, name))
            SoundPlayer.soundEffect(requireActivity())


            viewModel.jokerHandled()
        }


        viewModel.cardsDisabled.observe(viewLifecycleOwner){ isTrue ->
            if (isTrue) {
                binding.playerCard1.isEnabled = false
                binding.playerCard2.isEnabled = false
                binding.playerCard3.isEnabled = false
            }
        }
        viewModel.onWar()

        }

    fun yoyoOnAllWarCards(){

        val cards = listOf(
            binding.playerCard1,
            binding.playerCard2,
            binding.playerCard3,
            binding.opponentCard1,
            binding.opponentCard2,
            binding.opponentCard3
        )
        for (card in cards)  {YoYo.with(Techniques.Shake)
            .duration(1000)
            .repeat(0)
            .playOn(card)}
    }



    fun setTextView(text: String){
        binding.textViewWar.text = text.uppercase()
        binding.textViewWarStroke.text = text.uppercase()
    }
}