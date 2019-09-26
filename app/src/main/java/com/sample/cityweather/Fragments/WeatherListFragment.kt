package com.sample.cityweather.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DbWork.DataWorker

import com.sample.cityweather.R
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

class WeatherListFragment : Fragment() {
    private var listener: OnListFragmentInteractionListener? = null

    @Inject
    lateinit var dataWorker : DataWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }

    fun updateUi() {
//        val weatherAdapter =
//            WeatherAdapter(listOf(WeatherData("+20", "Moscow"), WeatherData("+30", "Bangkok")))
        val weatherAdapter =
            WeatherAdapter(dataWorker.getAllWeather())
        weatherRecyclerView!!.adapter = weatherAdapter
    }

    override fun onStart() {
        weatherRecyclerView!!.layoutManager = LinearLayoutManager(activity)
        updateUi()
        super.onStart()
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

    private inner class WeatherHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener?.onListFragmentInteraction(weather.city)
        }

        lateinit var weather: WeatherData

        var cityTextView: TextView = itemView.findViewById(R.id.cityTextView)
        var weatherTextView: TextView = itemView.findViewById(R.id.weatherTextView)

        fun bindWeather(weatherData: WeatherData) {
            cityTextView.text = weatherData.city
            weatherTextView.text = weatherData.weather
            weather = weatherData
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
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            WeatherListFragment()
    }
}
