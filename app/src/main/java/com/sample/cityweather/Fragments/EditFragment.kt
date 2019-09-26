package com.sample.cityweather.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DbWork.DataWorker

import com.sample.cityweather.R
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class EditFragment : Fragment() {
    private var listener: OnEditFragmentInteractionListener? = null

    @Inject
    lateinit var dataWorker: DataWorker

    private var weatherData: WeatherData? = null

    private var isNew : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
        val cityName = arguments?.getString(CITY_NAME)
        cityName?.let {
            weatherData = dataWorker.getWeather(cityName)
        }
        weatherData ?: let {
            weatherData = WeatherData()
            isNew = true
        }
    }

    override fun onStart() {
        super.onStart()
        saveBtn.setOnClickListener {
            weatherData?.let {

                weatherData!!.city = cityEdit.text.toString()

                //TODO change primary key
                if(isNew){
                    dataWorker.addWeather(weatherData!!)
                }
                else {
                    dataWorker.updateWeather(weatherData!!)
                }
            }
            listener?.onFragmentInteraction("close")
        }
        updateUi()
    }

    fun updateUi() {
        cityEdit.setText(weatherData?.city)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnEditFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnEditFragmentInteractionListener {
        fun onFragmentInteraction(string: String)
    }

    companion object {

        private const val CITY_NAME = "city"

        @JvmStatic
        fun newInstance(city: String?): EditFragment {
            val bundle = Bundle()
            bundle.putSerializable(CITY_NAME, city)

            val editFragment = EditFragment()
            editFragment.arguments = bundle
            return editFragment
        }
    }
}
