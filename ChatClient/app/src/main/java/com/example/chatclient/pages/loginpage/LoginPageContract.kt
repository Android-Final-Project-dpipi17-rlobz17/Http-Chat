package com.example.chatclient.pages.loginpage

interface LoginPageContract {

    interface View {
        fun onLoginSuccess()
        fun onLoginFailure()
    }

    interface Presenter {
        fun checkLogin(nickname: String, about: String, profile_picture : String?)
    }

    interface Model {
        fun checkLogin(nickname: String, about: String, profile_picture : String?) : Boolean
    }
}