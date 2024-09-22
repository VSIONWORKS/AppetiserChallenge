package com.exam.appetiserchallenge.data.service

import com.exam.appetiserchallenge.data.model.ItunesDataModel
import com.exam.appetiserchallenge.utils.Constants.BASE_URL
import com.exam.appetiserchallenge.utils.Constants.FETCH_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Data layer class for api calls and networking
 * [Retrofit] setup here
 * */
interface ApiService {

    @GET(FETCH_URL)
    suspend fun getItunesData(): ItunesDataModel

    companion object {
        operator fun invoke(): ApiService {

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val okHttpClient = OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}