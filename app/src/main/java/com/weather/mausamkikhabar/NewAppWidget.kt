package com.weather.mausamkikhabar

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.weather.mausamkikhabar.constants.AppConstants
import com.weather.mausamkikhabar.util.CityListHelper
import com.weather.mausamkikhabar.data.model.ForecastInfo
import com.weather.mausamkikhabar.data.retrofit.RetrofitClient
import com.weather.mausamkikhabar.util.getWeatherIcon
import com.weather.mausamkikhabar.util.kelvinToCelsius
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    private val mSharedPrefFile = "com.weather.mausamkikhabar.appwidgetsample"
    private val COUNT_KEY = "count"

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun getForecastInfo(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        if (CityListHelper.getSavedCitiesList().isNotEmpty()) {
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)

            val cityId: Int = CityListHelper.getSavedCitiesList().first().id!!

            val call = RetrofitClient.getApiInterface().getWeatherForecastForCityId(cityId, AppConstants.API_KEY)

            call.enqueue(object : Callback<ForecastInfo> {
                override fun onFailure(call: Call<ForecastInfo>, t: Throwable) {
                    Log.e("onFailure", "onFailure")
                }

                override fun onResponse(call: Call<ForecastInfo>, response: Response<ForecastInfo>) {

                    views.setTextViewText(
                        R.id.txtTemp,
                        "${kelvinToCelsius(response.body()?.list?.first()?.temp?.day!!)} \u2103"
                    )

                    val date = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date())
                    views.setTextViewText(R.id.txtDate, date)


                    views.setTextViewText(R.id.txtCityName, response.body()?.city?.name)
                    views.setTextViewText(
                        R.id.txtWeatherInfo,
                        response.body()?.list?.first()?.weather?.first()?.description?.capitalize()
                    )

                    views.setImageViewResource(
                        R.id.imgWeatherIcon,
                        getWeatherIcon(response.body()?.list?.first()?.weather?.first()?.icon!!)
                    )
                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views)

                }
            })
        } else {
            Toast.makeText(context, "Please select a city first", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateAppWidget(
        context: Context, appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        val prefs = context.getSharedPreferences(mSharedPrefFile, 0)
        var count = prefs.getInt(COUNT_KEY + appWidgetId, 0)
        count++

        val intentUpdate = Intent(context, NewAppWidget::class.java)
        intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        val idArray = intArrayOf(appWidgetId)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        val pendingUpdate = PendingIntent.getBroadcast(
            context, appWidgetId, intentUpdate,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate);
        getForecastInfo(context, appWidgetManager, appWidgetId)

        val prefEditor = prefs.edit()
        prefEditor.putInt(COUNT_KEY + appWidgetId, count)
        prefEditor.apply()

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

