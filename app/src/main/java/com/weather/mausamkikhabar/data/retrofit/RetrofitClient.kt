package com.weather.mausamkikhabar.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient {


    private var retrofit: Retrofit? = null
    private val BASE_URL: String = "https://api.openweathermap.org/data/2.5/"
    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        val httpClient = OkHttpClient.Builder()
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)

        httpClient.addInterceptor(loggingInterceptor)

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    companion object {

        private var sInstance: RetrofitClient? = null


        fun create() {
            sInstance = RetrofitClient()
        }

        fun retrofit(): Retrofit? {
            return sInstance!!.retrofit
        }


        fun getApiInterface(): ApiInterface {
            return retrofit()!!.create(ApiInterface::class.java)
        }

    }
}
