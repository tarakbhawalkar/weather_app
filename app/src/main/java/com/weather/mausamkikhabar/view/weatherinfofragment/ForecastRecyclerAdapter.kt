package com.weather.mausamkikhabar.view.weatherinfofragment

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.mausamkikhabar.data.model.ForecastData
import com.weather.mausamkikhabar.databinding.ItemForecastBinding
import com.weather.mausamkikhabar.util.capitalizeWords
import com.weather.mausamkikhabar.util.getShortDayFromTimeStamp
import com.weather.mausamkikhabar.util.setWeatherIcon

/**
 * Created on 22-Jul-19.
 */
class ForecastRecyclerAdapter(var forecastList: List<ForecastData?>) :
    RecyclerView.Adapter<ForecastRecyclerAdapter.ViewHolder>() {

    private var width: Int = Resources.getSystem().displayMetrics.widthPixels

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemForecastBinding.inflate(inflater, parent, false)

        val lp = itemBinding.root.layoutParams
        lp.width = (width / 7)
        itemBinding.root.layoutParams = lp
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastData = forecastList[position]
        setWeatherIcon(holder.mBinding.imgWeatherIcon, forecastData?.weather?.first()?.icon!!)
        getShortDayFromTimeStamp(holder.mBinding.txtDate, forecastData.dt!!)
        holder.mBinding.txtDescription.text = forecastData.weather?.first()?.description?.capitalizeWords()
        holder.mBinding.txtDescription.isSelected = true
    }

    class ViewHolder(var mBinding: ItemForecastBinding) : RecyclerView.ViewHolder(mBinding.root)
}