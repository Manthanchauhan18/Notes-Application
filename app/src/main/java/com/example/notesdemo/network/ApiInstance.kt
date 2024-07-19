package com.example.notesdemo.network

import com.example.notesdemo.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiInstance {

    //used local node.js code
    val okHttpClient = OkHttpClient.Builder().protocols(Collections.singletonList(Protocol.HTTP_1_1))
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .readTimeout(10 , TimeUnit.SECONDS)
        .writeTimeout(10 , TimeUnit.SECONDS)
        .connectTimeout(10 , TimeUnit.SECONDS)
        .build()

    val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiInterface = retrofit.create(ApiInterface::class.java)

}