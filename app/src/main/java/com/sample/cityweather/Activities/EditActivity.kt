package com.sample.cityweather.Activities

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.EditFragment

class EditActivity : SingleFragmentActivity(),
    EditFragment.OnEditFragmentInteractionListener {
    override fun onFragmentInteraction(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createFragment(): Fragment {
        return EditFragment.newInstance()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }
}