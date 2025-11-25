package com.example.warcardgame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.warcardgame.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    interface StartFragmentListener {
        fun startButtonClicked(username: String)
    }

    var ownerActivity: StartFragmentListener? = null

    lateinit var binding: FragmentStartBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            ownerActivity = context as StartFragmentListener
            Log.i("SOUT", "Start Fragment Listener was implemented successfully")
        } catch (e: Exception) {
            Log.e("SOUT", "Start Fragment Listener NOT implemented..!")
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            val player1 = binding.etUsernameInput.text.toString()

            ownerActivity?.startButtonClicked(player1)
        }
    }

    override fun onDetach() {
        super.onDetach()

        ownerActivity = null
    }


}