package com.weather.mausamkikhabar.util

import com.weather.mausamkikhabar.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created on 23-Jul-19.
 */

fun kelvinToCelsius(tempInKelvin: Double): String {
    return DecimalFormat("##.#").format((tempInKelvin - 273.15))
}

fun getDayFromTimeStamp(timeStamp: Long): String? {
    val calendar = Calendar.getInstance()
    calendar.timeZone = TimeZone.getDefault()
    calendar.timeInMillis = timeStamp * 1000

    return SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault()).format(calendar.time)

}


fun getWeatherIcon(iconId: String): Int {
    return when (iconId.toLowerCase()) {
        "01d" -> {
            R.drawable.d01
        }
        "01n" -> {
            R.drawable.n01
        }
        "02d" -> {
            R.drawable.d02
        }
        "02n" -> {
            R.drawable.n02
        }
        "03d" -> {
            R.drawable.d03
        }
        "03n" -> {
            R.drawable.n03
        }
        "04d" -> {
            R.drawable.d04
        }
        "04n" -> {
            R.drawable.n04
        }
        "09d" -> {
            R.drawable.d09
        }
        "09n" -> {
            R.drawable.n09
        }
        "10d" -> {
            R.drawable.d10
        }
        "10n" -> {
            R.drawable.n10
        }
        "11d" -> {
            R.drawable.d11
        }
        "11n" -> {
            R.drawable.n11
        }
        "13d" -> {
            R.drawable.d13
        }
        "13n" -> {
            R.drawable.n13
        }
        "50d" -> {
            R.drawable.d50
        }
        "50n" -> {
            R.drawable.n50
        }

        else -> {
            R.drawable.d01
        }
    }
}

fun getWindDirection(d: Int): String {

    val direction = (d % 360)

    if (direction > 348.75 || direction <= 11.25) {
        return "N"
    }
    if (direction > 11.25 && direction <= 33.75) {
        return "NNE"
    }
    if (direction > 33.75 && direction <= 56.25) {
        return "NE"
    }
    if (direction > 56.25 && direction <= 78.75) {
        return "ENE"
    }
    if (direction > 78.75 && direction <= 101.25) {
        return "E"
    }
    if (direction > 101.25 && direction <= 123.75) {
        return "ESE"
    }
    if (direction > 123.75 && direction <= 146.25) {
        return "SE"
    }
    if (direction > 146.25 && direction <= 168.75) {
        return "SSE"
    }
    if (direction > 168.75 && direction <= 191.25) {
        return "S"
    }
    if (direction > 191.25 && direction <= 213.75) {
        return "SSW"
    }
    if (direction > 213.75 && direction <= 236.25) {
        return "SW"
    }
    if (direction > 236.25 && direction <= 258.75) {
        return "WSW"
    }
    if (direction > 258.75 && direction <= 281.25) {
        return "W"
    }
    if (direction > 281.25 && direction <= 303.75) {
        return "WNW"
    }
    if (direction > 303.75 && direction <= 326.25) {
        return "NW"
    }
    if (direction > 326.25 && direction <= 348.75) {
        return "WNW"
    }
    return ""
}


fun getWindImageRotation(d: Int): Double {

    val direction = ((360 - d) % 360)
    if (direction > 348.75 || direction <= 11.25) {
        return 0.0
    }
    if (direction > 11.25 && direction <= 33.75) {
        return 22.5
    }
    if (direction > 33.75 && direction <= 56.25) {
        return 45.0
    }
    if (direction > 56.25 && direction <= 78.75) {
        return 67.5
    }
    if (direction > 78.75 && direction <= 101.25) {
        return 90.0
    }
    if (direction > 101.25 && direction <= 123.75) {
        return 112.5
    }
    if (direction > 123.75 && direction <= 146.25) {
        return 135.0
    }
    if (direction > 146.25 && direction <= 168.75) {
        return 157.5
    }
    if (direction > 168.75 && direction <= 191.25) {
        return 180.0
    }
    if (direction > 191.25 && direction <= 213.75) {
        return 200.5
    }
    if (direction > 213.75 && direction <= 236.25) {
        return 225.0
    }
    if (direction > 236.25 && direction <= 258.75) {
        return 247.5
    }
    if (direction > 258.75 && direction <= 281.25) {
        return 270.0
    }
    if (direction > 281.25 && direction <= 303.75) {
        return 292.5
    }
    if (direction > 303.75 && direction <= 326.25) {
        return 315.0
    }
    if (direction > 326.25 && direction <= 348.75) {
        return 337.5
    }
    return 0.0
}

