package com.sample.cityweather.activities

import android.view.Menu
import androidx.fragment.app.Fragment
import com.sample.cityweather.fragments.WeatherListFragment
import com.sample.cityweather.R

class MainActivity : SingleFragmentActivity(),
    WeatherListFragment.OnListFragmentInteractionListener {

    private var menu: Menu? = null

    override fun onListFragmentInteraction(string: String) {
    }

    override fun createFragment(): Fragment {
        return WeatherListFragment.newInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        this.menu = menu
        return true
    }
}
