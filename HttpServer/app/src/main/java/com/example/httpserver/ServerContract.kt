package com.example.httpserver

import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.user.UserEntity
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
        fun getUserByNickName(nickName: String): UserEntity
        fun getChatMessages(firstUserName: String, secondUserName: String): MutableList<MessageEntity>
        fun saveMessage(message: MessageEntity)
    }
}