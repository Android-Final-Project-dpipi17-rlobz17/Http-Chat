package com.example.chatclient.pages.connectpage

import java.lang.Thread.sleep

class ConnectPageModelImpl(var presenter: ConnectPageContract.Presenter) : ConnectPageContract.Model {

    override fun checkConnection(): Boolean {
        // TODO[DP] check service connection
        sleep(5000)
        return false;
    }

}