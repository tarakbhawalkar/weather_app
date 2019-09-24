package com.weather.mausamkikhabar.view.weatherinfofragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.weather.mausamkikhabar.R
import com.weather.mausamkikhabar.data.model.City
import com.weather.mausamkikhabar.data.model.ForecastData
import com.weather.mausamkikhabar.data.model.ForecastInfo
import com.weather.mausamkikhabar.databinding.FragmentWeatherInfoBinding
import com.weather.mausamkikhabar.util.*
import com.weather.mausamkikhabar.view.mainactivity.MainActivity
import com.weather.mausamkikhabar.viewmodel.WeatherInfoFragmentViewModel
import kotlinx.android.synthetic.main.fragment_weather_info.*


class WeatherInfoFragment : Fragment() {

    private lateinit var mBinding: FragmentWeatherInfoBinding
    private lateinit var mViewModel: WeatherInfoFragmentViewModel

    private lateinit var bottomSheet: BottomSheetBehavior<View>

    private val backgroundColor = Color.rgb(255, 253, 231)
    private val fillColor = Color.argb(150, 255, 138, 101)

    private val forecastDataObserver = Observer<ForecastInfo> { forecastInfo ->

        mBinding.txtCityName.text = forecastInfo?.city?.name
        val forecastData = forecastInfo?.list?.first()
        setWeatherIcon(mBinding.imgWeather, forecastData?.weather?.first()?.icon!!)
        mBinding.txtTemp.text = "${kelvinToCelsius(forecastData.temp?.day!!)} \u2103"
        mBinding.txtMinTemp.text = "Min\n ${kelvinToCelsius(forecastData.temp?.min!!)} \u2103"
        mBinding.txtMaxTemp.text = "Max\n ${kelvinToCelsius(forecastData.temp?.max!!)} \u2103"
        mBinding.txtWeatherDate.text =
            "${getDayFromTimeStamp(forecastData.dt!!)}\n${forecastData.weather?.first()?.description?.capitalize()}"
        val weatherDataList = ArrayList<ForecastData?>()
        weatherDataList.addAll(forecastInfo.list!!)
        weatherDataList.remove(weatherDataList.first())
        mBinding.recyclerForecastData.adapter = WeatherForecastRecyclerAdapter(weatherDataList)
        mBinding.recyclerForecast.apply {
            adapter = ForecastRecyclerAdapter(forecastInfo.list!!)
        }

        val deg: Int = forecastData.deg!!
        val matrix = android.graphics.Matrix()
        mBinding.imgWindDirection.scaleType = ImageView.ScaleType.MATRIX   //required
        matrix.postRotate(
            (-getWindImageRotation(deg).toFloat()),
            (mBinding.imgWindDirection.drawable.bounds.width() / 2).toFloat(),
            (mBinding.imgWindDirection.drawable.bounds.height() / 2).toFloat()
        )
        mBinding.imgWindDirection.imageMatrix = matrix

        mBinding.txtWindDirection.text = getWindDirection(deg)
        mBinding.chart.setGridBackgroundColor(fillColor)
        mBinding.chart.setDrawGridBackground(true)
        mBinding.chart.setPinchZoom(false)


        val xAxis = mBinding.chart.xAxis
        xAxis.isEnabled = false

        mBinding.chart.isScaleXEnabled = false
        mBinding.chart.isScaleYEnabled = false

        mBinding.chart.axisLeft.isEnabled = false
        mBinding.chart.axisRight.isEnabled = false
        mBinding.chart.description.isEnabled = false
        mBinding.chart.legend.isEnabled = false
        mBinding.chart.legend.form = Legend.LegendForm.LINE


        setData(forecastInfo.list!!)
        mBinding.chart.invalidate()
    }

    private fun setData(forecastInfo: ArrayList<ForecastData?>?) {

        val values1 = ArrayList<Entry?>()
        val values2 = ArrayList<Entry?>()
        val minTemp = mutableListOf<Float>()
        val maxTemp = mutableListOf<Float>()
        val leftAxis = mBinding.chart.axisLeft


        forecastInfo?.forEachIndexed { index, it ->
            values1.add(
                Entry(
                    index.toFloat(),
                    kelvinToCelsius(it?.temp?.min!!).toFloat()
                )
            )
            values2.add(
                Entry(
                    index.toFloat(),
                    kelvinToCelsius(it.temp?.max!!).toFloat()
                )
            )

            minTemp.add(kelvinToCelsius(it.temp?.min!!).toFloat())
            maxTemp.add(kelvinToCelsius(it.temp?.max!!).toFloat())

        }

/*
        for (index in 0 until forecastInfo?.size!!) {

            values1.add(
                Entry(
                    index.toFloat(),
                    kelvinToCelsius(forecastInfo.get(index)?.temp?.min!!).toFloat()
                )
            )
            values2.add(
                Entry(
                    index.toFloat(),
                    kelvinToCelsius(forecastInfo.get(index)?.temp?.max!!).toFloat()
                )
            )

            minTemp.add(kelvinToCelsius(forecastInfo.get(index)?.temp?.min!!).toFloat())
            maxTemp.add(kelvinToCelsius(forecastInfo.get(index)?.temp?.max!!).toFloat())

        }
*/

        leftAxis.axisMinimum = minTemp.min()!! - 1
        leftAxis.axisMaximum = maxTemp.max()!! + 1

        val set1: LineDataSet
        val set2: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = mBinding.chart.data.getDataSetByIndex(0) as LineDataSet
            set2 = mBinding.chart.data.getDataSetByIndex(1) as LineDataSet
            set1.values = values1
            set2.values = values2
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values1, "Min Temp")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = Color.rgb(255, 87, 34)
            set1.setDrawCircles(false)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.fillAlpha = 255
            set1.setDrawFilled(true)
            set1.fillColor = backgroundColor
            set1.setDrawCircleHole(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider ->
                // change the return value here to better understand the effect
                // return 0;
                mBinding.chart.axisLeft.axisMinimum
            }
            set1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER


            // create a dataset and give it a type
            set2 = LineDataSet(values2, "Max Temp")
            set2.axisDependency = YAxis.AxisDependency.LEFT
            set2.color = Color.rgb(255, 87, 34)
            set2.setDrawCircles(false)
            set2.lineWidth = 1f
            set2.circleRadius = 3f
            set2.fillAlpha = 255
            set2.setDrawFilled(true)
            set2.fillColor = backgroundColor
            set2.setDrawCircleHole(true)
            set2.fillFormatter = IFillFormatter { dataSet, dataProvider ->
                // change the return value here to better understand the effect
                // return 600;
                mBinding.chart.axisLeft.axisMaximum
            }
            set2.mode = LineDataSet.Mode.HORIZONTAL_BEZIER

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets
            dataSets.add(set2)

            // create a data object with the data sets
            val data = LineData(dataSets)
            data.isHighlightEnabled = false

            // set data
            mBinding.chart.data = data
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_weather_info,
            container,
            false
        )

        mViewModel = ViewModelProviders.of(this).get(WeatherInfoFragmentViewModel::class.java)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var city: City? = null
        arguments?.let {
            city = it.getParcelable("city")
        }

        bottomSheet = BottomSheetBehavior.from(mBinding.layoutBottomSheet)
        bottomSheet.isHideable = false
        bottomSheet.peekHeight = (resources.displayMetrics.heightPixels * .20).toInt()

        bottomSheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        (activity as MainActivity).mBinding.imgAddLocation.gone()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        (activity as MainActivity).mBinding.imgAddLocation.gone()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        (activity as MainActivity).mBinding.imgAddLocation.visible()

                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        (activity as MainActivity).mBinding.imgAddLocation.gone()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        (activity as MainActivity).mBinding.imgAddLocation.gone()
                    }
                }
            }

        })

        mViewModel.getForecastInfo(city?.id!!)

        mViewModel.getForecastInfoLiveData().observe(this, forecastDataObserver)

    }

}
