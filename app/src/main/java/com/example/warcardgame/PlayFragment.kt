package com.example.warcardgame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.warcardgame.databinding.FragmentPlayBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PlayFragment : Fragment() {

    interface PlayFragmentListener{
        fun dealButtonClicked(cardPlayer: ImageView, cardCPU: ImageView)
        fun exitButtonClicked()
    }

    var ownerActivity: PlayFragmentListener? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentPlayBinding


    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            ownerActivity = context as PlayFragmentListener
            Log.i("SOUT", "Play Fragment listener implemented successfully")
        } catch (e: Exception) {
            Log.e("SOUT", "Play Fragment listener NOT implemented")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val player1Name = arguments?.getString("player1_name") ?: "Player 1"
        val player2Name = arguments?.getString("player2_name") ?: "Player 2"

        binding.tvPlayer1Username.text = player1Name
        binding.tvPlayer2Username.text = player2Name

        binding.dealBtn.setOnClickListener {
            val card2: ImageView = binding.cardCpu
            val card1: ImageView = binding.cardPlayer

            ownerActivity?.dealButtonClicked(card1, card2)



        }

        binding.exitBtn.setOnClickListener {
            ownerActivity?.exitButtonClicked()
        }



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlayFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String, param2: String) =
                PlayFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onDetach() {
        super.onDetach()
        ownerActivity = null
    }
}