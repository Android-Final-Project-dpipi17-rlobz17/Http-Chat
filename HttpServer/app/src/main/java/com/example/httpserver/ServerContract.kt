package com.example.httpserver

import com.sun.net.httpserver.HttpExchange

interface ServerContract {

    interface View {
        fun onServerStart()
        fun onServerStop()
    }

    interface Presenter {
        fun startServer()
        fun stopServer()
        fun sendResponse(httpExchange: HttpExchange, responseText: String)
    }

    interface Model {

    }
}