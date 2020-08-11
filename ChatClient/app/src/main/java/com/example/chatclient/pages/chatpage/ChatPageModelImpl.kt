package com.example.chatclient.pages.chatpage

import com.example.chatclient.network.api.MessageService
import com.example.chatclient.network.dataclasses.MessageEntity
import com.example.chatclient.pages.chatpage.responsedataclasses.ChatPageResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ChatPageModelImpl(var presenter: ChatPageContract.Presenter) : ChatPageContract.Model {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:5000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun fetchMessages(myNickName: String, friendNickName: String) {

        val service: MessageService = retrofit.create<MessageService>(MessageService::class.java)
        val call = service.getMessages(myNickName, friendNickName)

        call.enqueue(object : Callback<ChatPageResponse> {
            override fun onResponse(call: Call<ChatPageResponse>, response: Response<ChatPageResponse>) {
                if (response.code() == 200) {
                    response.body()?.let {
                        presenter.onMessageFetch(it)
                    }
                }
            }
            override fun onFailure(call: Call<ChatPageResponse>, t: Throwable) {

            }
        })
    }

    override fun sendMessage(message: MessageEntity) {
        val service: MessageService = retrofit.create<MessageService>(MessageService::class.java)
        val call = service.sendMessage(message)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200) {
                    response.body()?.let {

                    }
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {

            }
        })
    }
}