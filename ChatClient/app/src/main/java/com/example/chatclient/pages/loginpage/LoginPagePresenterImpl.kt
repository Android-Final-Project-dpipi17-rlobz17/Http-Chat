package com.example.chatclient.pages.loginpage

import android.content.Context

class LoginPagePresenterImpl(var view: LoginPageContract.View, var context: Context) : LoginPageContract.Presenter {

    private var model = LoginPageModelImpl(this)

    override fun checkLogin(nickname: String, about: String, profile_picture: String?) {
        if(model.checkLogin(nickname,about,profile_picture)){
            view.onLoginSuccess()
        }else{
            view.onLoginFailure()
        }
    }

}