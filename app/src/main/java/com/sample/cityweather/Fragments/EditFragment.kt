package com.sample.cityweather.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DataWorkers.WeatherConverter
import com.sample.cityweather.DbWork.DataWorker

import com.sample.cityweather.R
import com.sample.cityweather.Retrofit.WeatherController
import com.sample.cityweather.Retrofit.DataCallback
import com.sample.cityweather.Retrofit.PictureController
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class EditFragment : Fragment() {
    private var listener: OnEditFragmentInteractionListener? = null

    @Inject
    lateinit var dataWorker: DataWorker

    @Inject
    lateinit var weatherController:WeatherController

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
        val id = arguments?.getString(ID)
        id?.let {
            weatherData = dataWorker.getWeather(id)
        }
        weatherData ?: let {
            weatherData = WeatherData()
            isNew = true
        }
        weatherData?.let {
            val cityName = weatherData?.city?:""
            if(!cityName.contentEquals("")) {
                val pictureController = PictureController()
                pictureController.start(object : DataCallback {
                    override fun setData(data: String?) {
                        Picasso.get().load(data).into(imageView)
                    }

                }, cityName)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        saveBtn.setOnClickListener {
            weatherData?.let {

                weatherData!!.city = cityEdit.text.toString()
                weatherData!!.locale = localeEdit.text.toString()

                //TODO set listeners
                if(isNew){
                    dataWorker.addWeather(weatherData!!)
                }
                else {
                    dataWorker.updateWeather(weatherData!!)
                }
            }
            listener?.onFragmentInteraction("close")
        }

        findWeatherBtn.setOnClickListener{
            weatherData?.let {
                weatherController.start(object : DataCallback{
                    override fun setData(data: String?) {
                        val res = WeatherConverter.convertKelvin(data)
                        weatherView.text = res
                        weatherData!!.weather = res
                    }
                }, weatherData!!)
            }
        }

        cityEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                weatherData?.city = s.toString()
            }

        })

        localeEdit.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                weatherData?.locale = s.toString()
            }

        })
        updateUi()
    }

    fun updateUi() {
        cityEdit.setText(weatherData?.city)
        localeEdit.setText(weatherData?.locale)
        weatherView.text = weatherData?.weather
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

        private const val ID = "id"

        @JvmStatic
        fun newInstance(id: String?): EditFragment {
            val bundle = Bundle()
            bundle.putSerializable(ID, id)

            val editFragment = EditFragment()
            editFragment.arguments = bundle
            return editFragment
        }
    }
}
