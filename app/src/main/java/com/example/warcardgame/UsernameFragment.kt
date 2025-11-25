package com.example.warcardgame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warcardgame.StartFragment.StartFragmentListener
import com.example.warcardgame.databinding.FragmentUsernameBinding
import kotlin.toString


class UsernameFragment : Fragment() {
   interface UsernameFragmentListener{
       fun usernameButtonClicked(username: String)
   }

    var ownerActivity: UsernameFragmentListener? = null
    lateinit var binding: FragmentUsernameBinding

    private var param1: String? = null
    private var param2: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            ownerActivity = context as UsernameFragmentListener
            Log.i("SOUT", "Username Fragment Listener was implemented successfully")
        } catch (e: Exception) {
            Log.e("SOUT", "Username Fragment Listener NOT implemented..!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalPlayers = arguments?.getInt("totalPlayers") ?: 1
        if (totalPlayers == 2) {
            binding.btnStart.setImageResource(R.drawable.next_player)

        }

        binding.btnStart.setOnClickListener {
            val name = binding.etUsernameInput.text.toString()
            if(name.isNotEmpty()) {
            ownerActivity?.usernameButtonClicked(name)
            binding.etUsernameInput.text.clear()
                binding.btnStart.setImageResource(R.drawable.start_btn)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        ownerActivity = null
    }


}