package com.example.warcardgame


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.warcardgame.databinding.FragmentStartBinding


class StartFragment : Fragment() {
    lateinit var viewModel: GameViewModel
    lateinit var binding: FragmentStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.startGameBtn.setOnClickListener {
            val name = binding.etUsernameInput.text.toString()
            if (name.isNotEmpty()) {
                viewModel.startGame(name)

                parentFragmentManager.beginTransaction().apply {
                    val playFragment = PlayFragment()
                    replace(R.id.mainContainer, playFragment)
                    commit()
                }
                binding.etUsernameInput.text.clear()
            }
        }

        binding.exitBtn.setOnClickListener {
            requireActivity().finish()
        }


    }


}