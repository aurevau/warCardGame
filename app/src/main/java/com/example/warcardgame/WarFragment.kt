package com.example.warcardgame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.postDelayed
import androidx.lifecycle.ViewModelProvider
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


        viewModel.onWar()

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
                }, 1000)

                viewModel.doneNavigatingToPlay()
            }

        }

        viewModel.warWinnerName.observe(viewLifecycleOwner){name ->
            binding.textViewWar.text = getString(R.string.winner_war, name)
            binding.textViewWarStroke.text = getString(R.string.winner_war, name)
        }

        viewModel.warAnnouncement.observe(viewLifecycleOwner){text ->
        binding.textViewWar.text = text
            binding.textViewWarStroke.text = text


    }
    }
}