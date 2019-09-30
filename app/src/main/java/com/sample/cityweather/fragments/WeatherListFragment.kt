package com.sample.cityweather.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.cityweather.activities.EditActivity
import com.sample.cityweather.dataClasses.WeatherData
import com.sample.cityweather.mvpViews.WeatherListView
import com.sample.cityweather.presenters.WeatherListPresenter

import com.sample.cityweather.R
import kotlinx.android.synthetic.main.fragment_list.*

class WeatherListFragment : MvpAppCompatFragment(),
    WeatherListView {

    private var listener: OnListFragmentInteractionListener? = null

    @InjectPresenter
    lateinit var weatherListPresenter: WeatherListPresenter

    private val REQUEST_CODE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun updateWeather(position: Int) {
        weatherRecyclerView.adapter?.notifyItemChanged(position)
    }

    override fun updateUi(weatherList: List<WeatherData>) {
        val weatherAdapter =
            WeatherAdapter(weatherList)
        weatherRecyclerView!!.adapter = weatherAdapter
        weatherAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        weatherListPresenter.onStart()
        weatherRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateBtn.setOnClickListener {
            weatherListPresenter.onUpdateBtnClick()
        }
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        weatherListPresenter.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                weatherRecyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addItem -> {
                val context = activity
                context?.let {
                    startActivityForResult(EditActivity.newIntent(context), REQUEST_CODE)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private inner class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = activity
            context?.let {
                startActivityForResult(
                    EditActivity.newIntent(context, weather.id.toString()),
                    REQUEST_CODE
                )
            }
        }

        lateinit var weather: WeatherData

        var cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        var weatherTextView: TextView = itemView.findViewById(R.id.weatherTextView)
        var deleteBtn: Button = itemView.findViewById(R.id.deleteBtn)

        fun bindWeather(weatherData: WeatherData, position: Int) {
            cityTextView.text = weatherData.city
            weatherTextView.text = weatherData.weather
            weather = weatherData
            deleteBtn.setOnClickListener {
                weatherListPresenter.onDeleteBtnClick(weather)
            }
            weatherListPresenter.onItemUpdate(weather, position)
        }
    }

    private inner class WeatherAdapter(weatherListExternal: List<WeatherData>) :
        RecyclerView.Adapter<WeatherHolder>() {

        var weatherList: List<WeatherData> = weatherListExternal

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
            val layoutInflater = LayoutInflater.from(activity)
            val view = layoutInflater.inflate(R.layout.list_item_weather, parent, false)
            return WeatherHolder(view)
        }

        override fun getItemCount(): Int {
            return weatherList.size
        }

        override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
            holder.bindWeather(weatherList[position], position)
        }
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(string: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WeatherListFragment()
    }
}
