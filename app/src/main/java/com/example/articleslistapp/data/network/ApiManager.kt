package com.example.articleslistapp.data.network


import com.example.articleslistapp.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiManager {

    private var gson: Gson

    init {
        gson = createGson()
    }

    fun create(): RestApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .build()

        return retrofit.create(RestApi::class.java)
    }

    fun createGson(): Gson {
        return GsonBuilder().apply {
            setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            setLenient()
        }.create()
    }

}