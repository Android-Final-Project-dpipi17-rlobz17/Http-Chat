package com.example.chatclient.pages.historypage

import com.example.chatclient.network.api.MessageService
import com.example.chatclient.network.dataclasses.HistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HistoryPageModelImpl (var presenter: HistoryPageContract.Presenter) : HistoryPageContract.Model{

    override fun getData(userNickname: String, searchText: String, index: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: MessageService = retrofit.create<MessageService>(MessageService::class.java)
        val call = service.getHistory(userNickname, index, searchText)


        Thread.sleep(1000)

        call.enqueue(object : Callback<List<HistoryResponse>> {
            override fun onResponse(call: Call<List<HistoryResponse>>, response: Response<List<HistoryResponse>>) {
                if (response.code() == 200) {
                    presenter.updateData(response.body()!!, searchText)
                }
            }

            override fun onFailure(call: Call<List<HistoryResponse>>, t: Throwable) {

            }
        })
    }

    override fun removeMessages(nickname: String) {
        TODO("Not yet implemented")
    }

}