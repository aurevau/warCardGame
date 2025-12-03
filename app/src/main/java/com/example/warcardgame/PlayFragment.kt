package com.example.warcardgame

import android.R.attr.rotationY
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.warcardgame.databinding.FragmentPlayBinding


class PlayFragment : Fragment() {
    lateinit var viewModel: GameViewModel
    lateinit var binding: FragmentPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dealBtn.setOnClickListener {
            viewModel.dealCard()
        }


        binding.exitBtn.setOnClickListener {
            viewModel.resetRoundWinner()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, StartFragment())
                commit()
            }
        }

        viewModel.player1Name.observe(viewLifecycleOwner) { name ->
            binding.tvPlayer1Username.text = name
        }

        viewModel.player2Name.observe(viewLifecycleOwner) { name ->
            binding.tvPlayer2Username.text = getString(R.string.cpu)
        }

        viewModel.announcement.observe(viewLifecycleOwner) { text ->
            if (text == "noCards") {
                binding.tvAnnouncement.text = getString(R.string.noCards)
            }
            binding.tvAnnouncement.text = text
        }

        viewModel.player1Score.observe(viewLifecycleOwner) { score ->
            binding.tvPlayer1Score.text = score.toString()
        }

        viewModel.player2Score.observe(viewLifecycleOwner) { score ->
            binding.tvPlayer2Score.text = score.toString()
        }

        viewModel.playerCardImage.observe(viewLifecycleOwner) { image ->
            binding.cardPlayer.setImageResource(image)
        }

        viewModel.opponentCardImage.observe(viewLifecycleOwner) { image ->
            binding.cardCpu.setImageResource(image)
        }

        viewModel.navigateToPlay.observe(viewLifecycleOwner) { backToPlay ->
            
            if (backToPlay == true) {
                parentFragmentManager.popBackStack()
                viewModel.doneNavigatingToPlay()
            }
        }

        viewModel.roundWinnerName.observe(viewLifecycleOwner) { name ->

            when (name) {
                "tie" -> binding.tvAnnouncement.text = getString(R.string.tie_text)
                null -> {}
                else -> binding.tvAnnouncement.text = getString(R.string.player_win, name)
            }

        }

        viewModel.firstRound.observe(viewLifecycleOwner) { ifTrue ->
            if (ifTrue == true && viewModel.roundWinnerName.value == null) {
                binding.tvAnnouncement.text = getString(R.string.game_start)
            }
        }

        viewModel.finalWinnerName.observe(viewLifecycleOwner) { name ->
            if (name != null) {
                binding.tvAnnouncement.text = getString(R.string.winner_is, name)
//             else {
//                binding.tvAnnouncement.text = getString(R.string.game_reset)
//            }

            }

        }

        viewModel.navigateToWar.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                binding.dealBtn.isEnabled = false
                binding.root.postDelayed({
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.mainContainer, WarFragment())
                        addToBackStack(null)
                        commit()
                    }
                }, 1000)

                viewModel.doneNavigatingToWar()
            }

        }
        viewModel.warAnnouncement.observe(viewLifecycleOwner) { text ->
            if (text == "noWarCards") {
                binding.tvAnnouncement.text = getString(R.string.noCardsForWar)
            }
        }

        viewModel.navigateToWinner.observe(viewLifecycleOwner) { shouldNavigate ->
            if (shouldNavigate == true) {
                binding.dealBtn.isEnabled = false
                val winnerFragment = WinnerFragment()
                binding.root.postDelayed({
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.mainContainer, winnerFragment)
                        commit()
                    }
                }, 1000)

                viewModel.doneNavigatingToWinner()


            }

        }
    }


}

