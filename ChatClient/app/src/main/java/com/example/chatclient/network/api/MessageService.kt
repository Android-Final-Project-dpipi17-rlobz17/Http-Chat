package com.example.chatclient.network.api

import com.example.chatclient.network.api.dataClasses.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageService {

    @GET("connection")
    fun checkConnection(): Call<Boolean>

    @POST("login")
    fun checkLogin (user: User): Call<Boolean>
}