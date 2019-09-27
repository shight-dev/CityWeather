package com.sample.cityweather.Activities

import android.view.Menu
import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.WeatherListFragment
import com.sample.cityweather.R

class MainActivity : SingleFragmentActivity(),
    WeatherListFragment.OnListFragmentInteractionListener {

    private var mMenu: Menu? = null

    override fun onListFragmentInteraction(string: String) {
    }

    override fun createFragment(): Fragment {
        return WeatherListFragment.newInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        mMenu = menu
        return true
    }
}
