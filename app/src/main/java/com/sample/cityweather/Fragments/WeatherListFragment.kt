package com.sample.cityweather.Fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.cityweather.Activities.EditActivity
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DataWorkers.WeatherConverter
import com.sample.cityweather.DbWork.DataWorker

import com.sample.cityweather.R
import com.sample.cityweather.Retrofit.WeatherController
import com.sample.cityweather.Retrofit.DataCallback
import com.sample.cityweather.Retrofit.PictureController
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class WeatherListFragment : Fragment() {
    private var listener: OnListFragmentInteractionListener? = null

    @Inject
    lateinit var dataWorker : DataWorker

    @Inject
    lateinit var weatherController : WeatherController

    private val REQUEST_CODE = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        setHasOptionsMenu(true)
    }

    private fun updateUi() {
        val weatherAdapter =
            WeatherAdapter(dataWorker.getAllWeather())
        weatherRecyclerView!!.adapter = weatherAdapter
        weatherAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        weatherRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateUi()
        updateBtn.setOnClickListener{
            updateUi()
        }
        super.onStart()
        val pictureController = PictureController()
        pictureController.start(object :DataCallback{
            override fun setData(data: String?) {
                val a =1
            }

        }, "Moscow")
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
        if(requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                weatherRecyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addItem -> {
                //TODO rework activity call
                startActivityForResult(EditActivity.newIntent(activity!!),REQUEST_CODE)
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
            //TODO rework activity call
            startActivityForResult(EditActivity.newIntent(activity!!, weather.id.toString()), REQUEST_CODE)
        }

        lateinit var weather: WeatherData

        //TODO is nessasary?
        var cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        var weatherTextView: TextView = itemView.findViewById(R.id.weatherTextView)
        var localeTextView: TextView = itemView.findViewById(R.id.localeTextView)
        var deleteBtn: Button = itemView.findViewById(R.id.deleteBtn)

        fun bindWeather(weatherData: WeatherData) {
            cityTextView.text = weatherData.city
            weatherTextView.text = weatherData.weather
            localeTextView.text = weatherData.locale
            weather = weatherData
            deleteBtn.setOnClickListener {
                dataWorker.removeWeather(weather)
                updateUi()
            }
            weatherController.start(object : DataCallback{
                override fun setData(data: String?) {
                    val weatherString =  WeatherConverter.convertKelvin(data)
                    weatherTextView.text = weatherString
                    weather.weather = weatherString
                    dataWorker.updateWeather(weather)
                }

            },weather)
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
            holder.bindWeather(weatherList[position])
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
