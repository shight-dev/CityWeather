package com.sample.cityweather.Activities

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.EditFragment

class EditActivity : SingleFragmentActivity(),
    EditFragment.OnEditFragmentInteractionListener {
    override fun onFragmentInteraction(string: String) {
        setResult(0)
        finish()
    }

    override fun createFragment(): Fragment {
        val cityName = intent.getStringExtra(CITY_NAME)
        return EditFragment.newInstance(cityName?:null)
    }

    companion object {

        const val CITY_NAME = "city"

        fun newIntent(context: Context, cityName : String): Intent {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(CITY_NAME, cityName)
            return intent
        }

        fun newIntent(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }
}