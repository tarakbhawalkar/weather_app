package com.weather.mausamkikhabar.view.cityselectionfragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.LocalBroadcastManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.mausamkikhabar.R
import com.weather.mausamkikhabar.data.model.City
import com.weather.mausamkikhabar.util.CityListHelper
import com.weather.mausamkikhabar.databinding.FragmentSelectCityBinding
import com.weather.mausamkikhabar.util.PreferenceManager
import com.weather.mausamkikhabar.util.gone
import com.weather.mausamkikhabar.util.visible
import com.weather.mausamkikhabar.viewmodel.CitySelectionViewModel

/**
 * Created on 22-Jul-19.
 */
class CitySelectionFragment : DialogFragment() {

    private lateinit var mBinding: FragmentSelectCityBinding
    private lateinit var mViewModel: CitySelectionViewModel
    private lateinit var cityList: MutableList<City>
    private lateinit var fullCityList: MutableList<City>
    private lateinit var cityListAdapter: CitySelectionRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_select_city, container, false)
        mViewModel = ViewModelProviders.of(this@CitySelectionFragment).get(CitySelectionViewModel::class.java)
        return mBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        fullCityList = CityListHelper.getCities(activity!!)

        val savedCityList = CityListHelper.getSavedCitiesList()
        fullCityList.removeAll(savedCityList)
        cityList = mutableListOf()
        cityList.addAll(fullCityList)
        cityListAdapter = CitySelectionRecyclerAdapter(cityList)
        isCancelable = false
        mBinding.recyclerSelectCity.apply {
            adapter = cityListAdapter
        }

        var isCancelable = arguments?.getBoolean("isCancelable", false)
        if (isCancelable!!) {
            mBinding.imgCancel.visible()
        } else {
            mBinding.imgCancel.gone()
        }
        mBinding.imgCancel.setOnClickListener {
            dismiss()
            sendBroadCast(false)
        }

        cityListAdapter.setOnCitySelectedListener(object : CitySelectionRecyclerAdapter.OnCitySelectedListener {
            override fun onCitySelected(city: City) {
                val savedCitiesList: MutableList<City> = CityListHelper.getSavedCitiesList()
                savedCitiesList.add(city)
                PreferenceManager.setString(
                    PreferenceManager.SAVED_CITIES_LIST,
                    CityListHelper.getJsonString(savedCitiesList)
                )
                sendBroadCast(true)
                dismiss()
            }
        })

        mBinding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                cityListAdapter.filter.filter(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

    override fun onResume() {
        super.onResume()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun sendBroadCast(isCityAdded: Boolean) {
        LocalBroadcastManager.getInstance(activity!!).sendBroadcast(
            Intent("update").putExtra("cityAdded", isCityAdded)
        )

    }
}