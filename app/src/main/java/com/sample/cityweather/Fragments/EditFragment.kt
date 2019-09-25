package com.sample.cityweather.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sample.cityweather.R

class EditFragment : Fragment() {
    private var listener: OnEditFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnEditFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnEditFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(string :String)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            EditFragment()
    }
}
