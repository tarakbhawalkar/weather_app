package com.weather.mausamkikhabar.view.weatherinfofragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.mausamkikhabar.data.model.ForecastData
import com.weather.mausamkikhabar.databinding.ItemForecastDataBinding
import com.weather.mausamkikhabar.util.getDayFromTimeStamp
import com.weather.mausamkikhabar.util.kelvinToCelsius
import com.weather.mausamkikhabar.util.setWeatherIcon

/**
 * Created on 22-Jul-19.
 */
class WeatherForecastRecyclerAdapter(var forecastList: List<ForecastData?>) :
    RecyclerView.Adapter<WeatherForecastRecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemHouseBinding = ItemForecastDataBinding.inflate(inflater, parent, false)
        return ViewHolder(itemHouseBinding)

    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastData = forecastList[position]
        setWeatherIcon(holder.mBinding.imgWeather, forecastData?.weather?.first()?.icon!!)
        getDayFromTimeStamp(holder.mBinding.txtDate, forecastData.dt!!)
        holder.mBinding.txtMaxTemp.text = "${kelvinToCelsius(forecastData?.temp?.max!!)} ℃"
        holder.mBinding.txtMinTemp.text = "${kelvinToCelsius(forecastData?.temp?.min!!)} ℃"

    }

    class ViewHolder(var mBinding: ItemForecastDataBinding) : RecyclerView.ViewHolder(mBinding.root)
}