package com.weather.mausamkikhabar.util

import android.view.View

/**
 * Created on 23-Jul-19.
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
