package com.example.lifenuggets.utils

import com.example.lifenuggets.controller.BASE_URL
import com.example.lifenuggets.model.network.ApiUser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


//create an instance of retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient().newBuilder().build())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //create the service user
object ApiUserClientService{
    val retrofitService: ApiUser by lazy { retrofit.create(ApiUser::class.java)}

}

