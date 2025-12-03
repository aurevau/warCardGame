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
            viewModel.resetGame()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mainContainer, StartFragment())
                commit()
            }
        }

        viewModel.player1Name.observe(viewLifecycleOwner) { name ->
            binding.tvPlayer1Username.text = name.uppercase()
            binding.tvPlayer1UsernameStroke.text = name.uppercase()

        }

        viewModel.player2Name.observe(viewLifecycleOwner) { name ->
            binding.tvPlayer2Username.text = getString(R.string.cpu)
            binding.tvPlayer2UsernameStroke.text =getString(R.string.cpu)
        }

        viewModel.jokerListener.observe(viewLifecycleOwner){name ->
            name ?: return@observe
            when  {
                name == viewModel.player1Name.value -> {
                    YoYo.with(Techniques.Shake)
                        .duration(1000)
                        .repeat(0)
                        .playOn(binding.cardPlayer)
                }

                name == viewModel.player2Name.value -> {
                    YoYo.with(Techniques.Shake)
                        .duration(1000)
                        .repeat(0)
                        .playOn(binding.cardCpu)

                }

            }

            binding.root.postDelayed({
                binding.dealBtn.isEnabled = true
            },1000)

            binding.dealBtn.isEnabled = false
            binding.tvAnnouncement.text = getString(R.string.joker, name)
            binding.tvAnnouncementStroke.text = getString(R.string.joker, name)
            SoundPlayer.soundEffect(requireActivity())
            viewModel.jokerHandled()
        }

        viewModel.announcement.observe(viewLifecycleOwner) { text ->
            if (text == "noCards") {
                binding.tvAnnouncement.text = getString(R.string.noCards)
                binding.tvAnnouncementStroke.text = getString(R.string.noCards)
            }
            binding.tvAnnouncement.text = text
            binding.tvAnnouncementStroke.text = text
        }

        viewModel.player1Score.observe(viewLifecycleOwner) { score ->
            binding.tvPlayer1Score.text = score.toString()
            binding.tvPlayer1ScoreStroke.text = score.toString()
        }

        viewModel.player2Score.observe(viewLifecycleOwner) { score ->
            binding.tvPlayer2Score.text = score.toString()
            binding.tvPlayer2ScoreStroke.text = score.toString()
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

            when  {
               name == "tie" -> {binding.tvAnnouncement.text = getString(R.string.tie_text).uppercase()
                          binding.tvAnnouncementStroke.text = getString(R.string.tie_text).uppercase()}
               name == "back" -> {
                    binding.tvAnnouncement.text = getString(R.string.back_from_war)
                    binding.tvAnnouncementStroke.text = getString(R.string.back_from_war)
                }
                name == null -> {
                    binding.tvAnnouncement.text = getString(R.string.game_start)
                    binding.tvAnnouncementStroke.text = getString(R.string.game_start)}
                else -> {
                    binding.tvAnnouncement.text = getString(R.string.player_win, name).uppercase()
                binding.tvAnnouncementStroke.text = getString(R.string.player_win, name).uppercase()}
            }

        }

        viewModel.firstRound.observe(viewLifecycleOwner) { ifTrue ->
            if (ifTrue == true && viewModel.roundWinnerName.value == null) {
                binding.tvAnnouncement.text = getString(R.string.game_start)
                binding.tvAnnouncementStroke.text = getString(R.string.game_start)
            }
        }

        viewModel.finalWinnerName.observe(viewLifecycleOwner) { name ->
            if (name != null) {
                binding.tvAnnouncement.text = getString(R.string.winner_is, name).uppercase()
                binding.tvAnnouncementStroke.text = getString(R.string.winner_is, name).uppercase()
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
                binding.tvAnnouncementStroke.text = getString(R.string.noCardsForWar)
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

