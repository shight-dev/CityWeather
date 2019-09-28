package com.sample.cityweather.Fragments

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.sample.cityweather.DaggerWork.App
import com.sample.cityweather.DataClasses.WeatherData
import com.sample.cityweather.DataWorkers.WeatherConverter
import com.sample.cityweather.DbWork.DataWorker
import com.sample.cityweather.Presenters.WeatherEditPresenter

import com.sample.cityweather.R
import com.sample.cityweather.Retrofit.WeatherController.WeatherController
import com.sample.cityweather.Retrofit.DataCallback
import com.sample.cityweather.Retrofit.PictureController.PictureController
import com.sample.cityweather.mvpViews.WeatherEditView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit.*
import javax.inject.Inject

class EditFragment : MvpAppCompatFragment(),WeatherEditView {
    private var listener: OnEditFragmentInteractionListener? = null

    @InjectPresenter
    lateinit var weatherEditPresenter: WeatherEditPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = arguments?.getString(ID)
        weatherEditPresenter.onCreate(id)
    }

    override fun onStart() {
        super.onStart()
        saveBtn.setOnClickListener {
            weatherEditPresenter.onSaveBtnClick()
            listener?.onFragmentInteraction("close")
        }

        findWeatherBtn.setOnClickListener {
            weatherEditPresenter.onRefreshDataBtnClick()
        }

        cityEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                weatherEditPresenter.onCityChange(s.toString())
            }

        })

        localeEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                weatherEditPresenter.onLocaleChange(s.toString())
            }

        })
        weatherEditPresenter.onStart()
    }

    override fun updateUi(cityName:String, locale:String, weather:String) {
        cityEdit.setText(cityName)
        localeEdit.setText(locale)
        weatherView.text = weather
    }

    override fun updateTemp(temp:String){
        weatherView.text = temp
    }

    override fun updateImage(bitmap:Bitmap){
        imageView.setImageBitmap(bitmap)
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
