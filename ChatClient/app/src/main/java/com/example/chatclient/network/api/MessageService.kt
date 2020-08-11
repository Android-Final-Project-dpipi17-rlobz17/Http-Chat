package com.example.chatclient.network.api

import com.example.chatclient.network.dataclasses.HistoryResponse
import com.example.chatclient.network.dataclasses.MessageEntity
import com.example.chatclient.network.dataclasses.UserEntity
import com.example.chatclient.pages.chatpage.responsedataclasses.ChatPageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageService {

    @GET("connection")
    fun checkConnection(): Call<Boolean>

    @POST("login")
    fun checkLogin (@Body userEntity: UserEntity): Call<Void>

    @GET("history")
    fun getHistory(@Query("clientNickName") clientNickName: String,
                   @Query("index") index: Int,
                   @Query("searchText") searchText : String) : Call<List<HistoryResponse>>

    @POST("history")
    fun removeHistory(@Query("clientNickName") clientNickName: String,
                      @Query("friendNickName") friendNickName: String) : Call<Boolean>

    
    @GET("messages")
    fun getMessages(@Query("clientNickName") clientNickName: String,
                    @Query("friendNickName") friendNickName: String): Call<ChatPageResponse>

    @POST("messages")
    fun sendMessage(@Body message: MessageEntity): Call<Void>
}