package com.example.warcardgame

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.example.warcardgame.PlayFragment.PlayFragmentListener
import com.example.warcardgame.databinding.FragmentMultiplayerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MultiplayerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MultiplayerFragment : Fragment() {

    interface MultiplayerFragmentListener{
        fun opponentDealButtonClicked(cardOpponent: ImageView)
        fun dealButtonClicked(cardPlayer: ImageView)
        fun exitButtonClicked()
    }

    lateinit var binding : FragmentMultiplayerBinding

    var ownerActivity: MultiplayerFragmentListener? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            ownerActivity = context as MultiplayerFragmentListener
            Log.i("SOUT", "Multiplayer Fragment listener implemented successfully")
        } catch (e: Exception) {
            Log.e("SOUT", "Multiplayer Fragment listener NOT implemented")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMultiplayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MultiplayerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MultiplayerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dealBtn.setOnClickListener {
            val card1: ImageView = binding.cardPlayer
            ownerActivity?.dealButtonClicked(card1)
        }

        binding.dealBtnOpponent.setOnClickListener {
            val card2: ImageView = binding.cardCpu

            ownerActivity?.dealButtonClicked(card2)
        }

        binding.exitBtn.setOnClickListener {
            ownerActivity?.exitButtonClicked()
        }

        binding.exitBtnDown.setOnClickListener {
            ownerActivity?.exitButtonClicked()
        }
    }
    override fun onDetach() {
        super.onDetach()
        ownerActivity = null
    }
}