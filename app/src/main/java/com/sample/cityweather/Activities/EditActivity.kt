package com.sample.cityweather.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.sample.cityweather.Fragments.WeatherEditFragment

class EditActivity : SingleFragmentActivity(),
    WeatherEditFragment.OnEditFragmentInteractionListener {

    override fun onFragmentInteraction(string: String) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun createFragment(): Fragment {
        val id = intent.getStringExtra(ID)
        return WeatherEditFragment.newInstance(id ?: null)
    }

    companion object {

        const val ID = "id"

        fun newIntent(context: Context, id: String): Intent {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(ID, id)
            return intent
        }

        fun newIntent(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }
}