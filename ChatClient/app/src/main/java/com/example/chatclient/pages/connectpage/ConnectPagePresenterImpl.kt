package com.example.chatclient.pages.connectpage

import android.content.Context

class ConnectPagePresenterImpl(var view: ConnectPageContract.View, var context: Context) : ConnectPageContract.Presenter {

    private var model: ConnectPageContract.Model = ConnectPageModelImpl(this)

    override fun checkConnection() {
        if (model.checkConnection()) {
            view.onConnectionSuccess()
        } else {
            view.onConnectionFailure()
        }
    }

}