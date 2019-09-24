package com.weather.mausamkikhabar.view.mainactivity

import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.weather.mausamkikhabar.R
import com.weather.mausamkikhabar.data.model.City
import com.weather.mausamkikhabar.util.CityListHelper
import com.weather.mausamkikhabar.databinding.ActivityMainBinding
import com.weather.mausamkikhabar.util.gone
import com.weather.mausamkikhabar.util.visible
import com.weather.mausamkikhabar.view.cityselectionfragment.CitySelectionFragment
import com.weather.mausamkikhabar.view.weatherinfofragment.WeatherInfoFragment
import com.weather.mausamkikhabar.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mBinding: ActivityMainBinding
    lateinit var mViewModel: MainActivityViewModel
    lateinit var weatherInfoPagerAdapter: WeatherInfoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        weatherInfoPagerAdapter = WeatherInfoPagerAdapter(supportFragmentManager)

        initWeatherInfo()

        mBinding.viewPagerHome.offscreenPageLimit = 5
        mBinding.imgAddLocation.setOnClickListener {
            it.isEnabled = false
            showCitySelectionDialog(isCancelable = true)
        }

    }

    private fun initWeatherInfo() {
        if (CityListHelper.getSavedCitiesList().isEmpty()) {
            showCitySelectionDialog(isCancelable = false)
            mBinding.imgAddLocation.gone()

        } else {
            mBinding.imgAddLocation.visible()
            val savedCitiesList: MutableList<City> = CityListHelper.getSavedCitiesList()
            weatherInfoPagerAdapter.fragments.clear()
            println(savedCitiesList.toString())
            for (city in savedCitiesList) {
                val mBundle = Bundle()
                mBundle.putParcelable("city", city)
                val fragment = WeatherInfoFragment()
                fragment.arguments = mBundle
                weatherInfoPagerAdapter.addFragment(fragment)
            }
            mBinding.viewPagerHome.adapter = weatherInfoPagerAdapter
        }
    }

    private val updateImageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val isCityAdded = intent.getBooleanExtra("cityAdded", false)
            if (isCityAdded) {
                initWeatherInfo()
                mBinding.viewPagerHome.currentItem = weatherInfoPagerAdapter.fragments.size - 1
            }
            mBinding.imgAddLocation.isEnabled = true
        }
    }

    private fun showCitySelectionDialog(isCancelable: Boolean) {
        val citySelectionFragment = CitySelectionFragment()
        var bundle = Bundle()
        bundle.putBoolean("isCancelable", isCancelable)
        citySelectionFragment.arguments = bundle
        citySelectionFragment.isCancelable = isCancelable
        citySelectionFragment.show(supportFragmentManager, "")
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this).registerReceiver(updateImageReceiver, IntentFilter("update"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateImageReceiver)
    }
}
