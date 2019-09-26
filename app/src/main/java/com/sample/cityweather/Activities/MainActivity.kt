package com.sample.cityweather.Activities

import android.content.Intent
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
        //startActivityForResult(EditActivity.newIntent(this, string), 10)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
