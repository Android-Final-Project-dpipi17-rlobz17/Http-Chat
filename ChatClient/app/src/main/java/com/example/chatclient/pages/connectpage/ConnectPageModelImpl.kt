package com.example.chatclient.pages.connectpage

import com.example.chatclient.network.api.MessageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Thread.sleep

class ConnectPageModelImpl(var presenter: ConnectPageContract.Presenter) : ConnectPageContract.Model {

    override fun checkConnection() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/")
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: MessageService = retrofit.create<MessageService>(MessageService::class.java)
        val call = service.checkConnection()

        sleep(1000)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200) {
                    presenter.connectionChecked(true)
                }
                presenter.connectionChecked(false)
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                presenter.connectionChecked(false)
            }
        })
    }

}