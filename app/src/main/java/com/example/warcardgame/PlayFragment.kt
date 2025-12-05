package com.example.warcardgame


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding.cardCpu.setImageResource(R.drawable.background_card)
        binding.cardPlayer.setImageResource(R.drawable.background_card)
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
            binding.tvPlayer2UsernameStroke.text = getString(R.string.cpu)
        }



        viewModel.jokerListener.observe(viewLifecycleOwner) { name ->
            name ?: return@observe
            when {
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
            }, 1000)

            binding.dealBtn.isEnabled = false
            setAnnouncementText(getString(R.string.joker, name))
            SoundPlayer.soundEffect(requireActivity())
            viewModel.jokerHandled()
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

        viewModel.roundWinnerName.observe(viewLifecycleOwner) { name ->

            when {
                name == "tie" -> {
                    setAnnouncementText(getString(R.string.tie_text))
                    binding.dealBtn.isEnabled = false
                }

                name == "back" -> {
                    setAnnouncementText(getString(R.string.back_from_war))
                }

                name == "noWarCards" -> {
                    setAnnouncementText(getString(R.string.noCardsForWar))
                    binding.dealBtn.isEnabled = true
                }

                name == null -> {
                    setAnnouncementText(getString(R.string.game_start))
                }

                else -> {
                    setAnnouncementText(getString(R.string.player_win, name))
                }
            }

        }

        viewModel.firstRound.observe(viewLifecycleOwner) { ifTrue ->
            if (ifTrue == true && viewModel.roundWinnerName.value == null) {
                setAnnouncementText(getString(R.string.game_start))
            }
        }

        viewModel.finalWinnerName.observe(viewLifecycleOwner) { name ->
            if (name != null) {
                setAnnouncementText(getString(R.string.winner_is, name))
                binding.dealBtn.isEnabled = false


            } else {
                binding.dealBtn.isEnabled = true
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
                }, 1500)

                viewModel.doneNavigatingToWar()
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

    fun setAnnouncementText(text: String) {
        binding.tvAnnouncement.text = text.uppercase()
        binding.tvAnnouncementStroke.text = text.uppercase()
    }


}

