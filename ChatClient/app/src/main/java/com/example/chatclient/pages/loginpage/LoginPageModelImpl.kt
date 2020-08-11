package com.example.chatclient.pages.loginpage

import com.example.chatclient.network.api.MessageService
import com.example.chatclient.network.dataclasses.UserEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginPageModelImpl (var presenter: LoginPageContract.Presenter) : LoginPageContract.Model {

    override fun checkLogin(nickname: String, about: String, profile_picture: String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:5000/")
//            .baseUrl("http://10.0.2.2:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: MessageService = retrofit.create<MessageService>(MessageService::class.java)
        val call = service.checkLogin(UserEntity(nickname, about, profile_picture))


        Thread.sleep(1000)

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.code() == 200) {
                    presenter.loginSucceeded(nickname)
                }else {
                    presenter.loginFailed()
                }
            }
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                presenter.loginFailed()
            }
        })
    }

}