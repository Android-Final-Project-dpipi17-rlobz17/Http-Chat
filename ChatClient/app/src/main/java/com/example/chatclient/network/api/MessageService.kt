package com.example.chatclient.network.api

import retrofit2.Call
import retrofit2.http.GET

interface MessageService {

    @GET("connection")
    fun checkConnection(): Call<Boolean>
}