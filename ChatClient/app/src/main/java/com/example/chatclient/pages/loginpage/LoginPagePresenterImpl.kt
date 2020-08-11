package com.example.chatclient.pages.loginpage

import android.content.Context
import com.example.chatclient.network.dataclasses.UserEntity

class LoginPagePresenterImpl(var view: LoginPageContract.View, var context: Context) : LoginPageContract.Presenter {

    private var model = LoginPageModelImpl(this)

    override fun checkLogin(nickname: String, about: String, profile_picture: String) {
        model.checkLogin(nickname,about,profile_picture)
    }

    override fun loginSucceeded(nickname: String) {
        view.onLoginSuccess(nickname)
    }

    override fun loginFailed() {
        view.onLoginFailure()
    }

}