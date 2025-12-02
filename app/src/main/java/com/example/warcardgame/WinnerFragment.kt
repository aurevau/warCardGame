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
//        val winner = arguments?.getString("winner") ?: "No winner announced"
//        binding.winnerTextView.text = "Winner is ${winner}"


        viewModel.winnerName.observe(viewLifecycleOwner){name->
            binding.winnerTextView.text = "Winner is ${name}"
        }
        YoYo.with(Techniques.Wave)
            .duration(1000)   // 1 sekund, valfritt
            .repeat(0)        // upprepa animationen, valfritt
            .playOn(binding.winnerTextView)
        YoYo.with(Techniques.Wave)
            .duration(1000)   // 1 sekund, valfritt
            .repeat(0)        // upprepa animationen, valfritt
            .playOn(binding.throphy)


    }
}