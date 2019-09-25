package com.sample.cityweather.Activities

import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.WeatherListFragment

class MainActivity : SingleFragmentActivity(),
    WeatherListFragment.OnListFragmentInteractionListener {

    override fun onListFragmentInteraction(string: String) {
        //TODO вызвать новое активити
        startActivity(EditActivity.newIntent(this))
    }

    override fun createFragment(): Fragment {
        return WeatherListFragment.newInstance()
    }
}
