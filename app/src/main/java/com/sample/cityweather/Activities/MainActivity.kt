package com.sample.cityweather.Activities

import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.WeatherListFragment

class MainActivity : SingleFragmentActivity(),
    WeatherListFragment.OnListFragmentInteractionListener {

    override fun onFragmentInteraction(string: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createFragment(): Fragment {
        return WeatherListFragment.newInstance()
    }
}
