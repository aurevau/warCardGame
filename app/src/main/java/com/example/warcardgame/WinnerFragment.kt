package com.example.warcardgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.warcardgame.databinding.FragmentWinnerBinding

class WinnerFragment : Fragment() {
    lateinit var viewModel : GameViewModel
    lateinit var binding : FragmentWinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWinnerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newGameButton = binding.newGameBtn
        val playAgainButton = binding.playAgainBtn


        viewModel.finalWinnerName.observe(viewLifecycleOwner){name->
            binding.winnerTextmain.text = getString(R.string.winner_is, name)
            binding.winnerTextStroke.text = getString(R.string.winner_is, name)
        }


        YoYo.with(Techniques.Wave)
            .duration(1000)
            .repeat(0)
            .playOn(binding.winnerTextmain)
        YoYo.with(Techniques.Wave)
            .duration(1000)
            .repeat(0)
            .playOn(binding.winnerTextStroke)
        YoYo.with(Techniques.Wave)
            .duration(1000)
            .repeat(0)
            .playOn(binding.throphy)


        newGameButton.postDelayed({
            newGameButton.visibility = View.VISIBLE
            YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .playOn(newGameButton)
            YoYo.with(Techniques.FadeIn)
        }, 1000)

        playAgainButton.postDelayed({
            playAgainButton.visibility = View.VISIBLE
            YoYo.with(Techniques.FadeIn)
                .duration(500)
                .repeat(0)
                .playOn(playAgainButton)
            YoYo.with(Techniques.FadeIn)
        }, 500)

        playAgainButton.setOnClickListener {
            viewModel.resetGame()
            parentFragmentManager.beginTransaction().apply{
                replace(R.id.mainContainer, PlayFragment())
                commit()

            }
        }

        newGameButton.setOnClickListener {
            val username = viewModel.player1Name.value ?: getString(R.string.player_1)
            viewModel.resetStartFragment(username)

            parentFragmentManager.beginTransaction().apply{
                replace(R.id.mainContainer, StartFragment())
                commit()
            }
        }


    }
}