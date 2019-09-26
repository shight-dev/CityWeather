package com.sample.cityweather.Activities

import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.WeatherListFragment
import com.sample.cityweather.R

class MainActivity : SingleFragmentActivity(),
    WeatherListFragment.OnListFragmentInteractionListener {

    private var mMenu: Menu? = null

    override fun onListFragmentInteraction(string: String) {
        //TODO вызвать новое активити
        //TODO waiting for result
        startActivity(EditActivity.newIntent(this))
    }

    override fun createFragment(): Fragment {
        return WeatherListFragment.newInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        mMenu = menu
        return true
    }

    //TODO waiting for result
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.addItem -> {
                startActivity(EditActivity.newIntent(this))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
