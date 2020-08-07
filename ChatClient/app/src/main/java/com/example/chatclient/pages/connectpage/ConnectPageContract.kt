package com.example.chatclient.pages.connectpage

interface ConnectPageContract {

    interface View {
        fun onConnectionSuccess()
        fun onConnectionFailure()
    }

    interface Presenter {
        fun checkConnection()
    }

    interface Model {
        fun checkConnection() : Boolean
    }
}