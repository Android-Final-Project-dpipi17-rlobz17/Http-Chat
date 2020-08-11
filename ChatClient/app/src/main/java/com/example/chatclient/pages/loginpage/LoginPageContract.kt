package com.example.chatclient.pages.loginpage

import com.example.chatclient.network.dataclasses.UserEntity

interface LoginPageContract {

    interface View {
        fun onLoginSuccess(nickname: String)
        fun onLoginFailure()
    }

    interface Presenter {
        fun checkLogin(nickname: String, about: String, profile_picture : String)
        fun loginSucceeded(nickname: String)
        fun loginFailed()
    }

    interface Model {
        fun checkLogin(nickname: String, about: String, profile_picture : String)
    }
}