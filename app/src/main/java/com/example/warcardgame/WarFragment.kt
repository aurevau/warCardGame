package com.example.warcardgame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.warcardgame.PlayFragment.PlayFragmentListener
import com.example.warcardgame.databinding.FragmentWarBinding

class WarFragment : Fragment() {

    interface WarFragmentListener{
        fun onWar(playerCard1: ImageView,
                  playerCard2: ImageView,
                  playerCard3: ImageView,
                  opponentCard1: ImageView,
                  opponentCard2: ImageView,
                  opponentCard3: ImageView)
    }
    lateinit var binding: FragmentWarBinding

    var ownerActivity: WarFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            ownerActivity = context as WarFragmentListener
            Log.i("SOUT", "War Fragment listener implemented successfully")
        } catch (e: Exception) {
            Log.e("SOUT", "War Fragment listener NOT implemented")
        }
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

        ownerActivity?.onWar(binding.playerCard1, binding.playerCard2, binding.playerCard3, binding.opponentCard1, binding.opponentCard2, binding.opponentCard3)
    }

    override fun onDetach() {
        super.onDetach()
        ownerActivity = null
    }
}