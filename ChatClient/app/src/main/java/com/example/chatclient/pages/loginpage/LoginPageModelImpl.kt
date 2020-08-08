package com.example.chatclient.pages.loginpage


class LoginPageModelImpl (var presenter: LoginPageContract.Presenter) : LoginPageContract.Model {

    override fun checkLogin(nickname: String, about: String, profile_picture: String?): Boolean {
        // TODO[RL] Not yet implemented
        Thread.sleep(3000)
        return false
    }

}