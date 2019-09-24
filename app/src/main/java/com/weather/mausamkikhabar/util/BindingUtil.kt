package com.weather.mausamkikhabar.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.weather.mausamkikhabar.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 23-Jul-19.
 */

@BindingAdapter("weatherIcon")
fun setWeatherIcon(view: ImageView, iconId: String) {
    when (iconId.toLowerCase()) {
        "01d" -> {
            view.setImageResource(R.drawable.d01)
        }
        "01n" -> {
            view.setImageResource(R.drawable.n01)
        }
        "02d" -> {
            view.setImageResource(R.drawable.d02)
        }
        "02n" -> {
            view.setImageResource(R.drawable.n02)
        }
        "03d" -> {
            view.setImageResource(R.drawable.d03)
        }
        "03n" -> {
            view.setImageResource(R.drawable.n03)
        }
        "04d" -> {
            view.setImageResource(R.drawable.d04)
        }
        "04n" -> {
            view.setImageResource(R.drawable.n04)
        }
        "09d" -> {
            view.setImageResource(R.drawable.d09)
        }
        "09n" -> {
            view.setImageResource(R.drawable.n09)
        }
        "10d" -> {
            view.setImageResource(R.drawable.d10)
        }
        "10n" -> {
            view.setImageResource(R.drawable.n10)
        }
        "11d" -> {
            view.setImageResource(R.drawable.d11)
        }
        "11n" -> {
            view.setImageResource(R.drawable.n11)
        }
        "13d" -> {
            view.setImageResource(R.drawable.d13)
        }
        "13n" -> {
            view.setImageResource(R.drawable.n13)
        }
        "50d" -> {
            view.setImageResource(R.drawable.d50)
        }
        "50n" -> {
            view.setImageResource(R.drawable.n50)
        }

    }
}


@BindingAdapter("forecastday")
fun getDayFromTimeStamp(textView: TextView, timeStamp: Long) {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getDefault()
    calendar.timeInMillis = timeStamp * 1000
    textView.text = SimpleDateFormat("EEEE, MMM dd", Locale.getDefault()).format(calendar.time)

}

fun getShortDayFromTimeStamp(textView: TextView, timeStamp: Long) {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getDefault()
    calendar.timeInMillis = timeStamp * 1000
    var sdf = SimpleDateFormat("EEE", Locale.getDefault())
    var date = sdf.format(calendar.time)
    if (sdf.format(Calendar.getInstance().time) == sdf.format(calendar.time)) {
        date = "today"
    }

    sdf = SimpleDateFormat("d/M", Locale.getDefault())
    date += "\n${sdf.format(calendar.time)}"
    textView.text = date.capitalizeWords()

}