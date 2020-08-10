package com.example.chatclient.pages.connectpage

import android.content.Context

class ConnectPagePresenterImpl(var view: ConnectPageContract.View, var context: Context) : ConnectPageContract.Presenter {

    private var model: ConnectPageContract.Model = ConnectPageModelImpl(this)

    override fun checkConnection() {
        model.checkConnection()
    }

    override fun connectionChecked(success: Boolean) {
        if (success) {
            view.onConnectionSuccess()
        } else {
            view.onConnectionFailure()
        }
    }

}