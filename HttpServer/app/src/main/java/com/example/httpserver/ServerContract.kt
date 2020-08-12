package com.example.httpserver

import com.example.httpserver.database.chat.ChatEntity
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.user.UserEntity
import com.sun.net.httpserver.HttpExchange

interface ServerContract {

    interface Presenter {
        fun startServer()
        fun stopServer()
        fun sendResponse(httpExchange: HttpExchange, responseText: String)
    }

    interface Model {
        fun getAllUsers(exceptUserNickName: String): List<UserEntity>
        fun getUserByNickName(nickName: String): UserEntity?
        fun saveUser(user: UserEntity)
        fun updateUser(user: UserEntity)

        fun getChatMessages(firstUserName: String, secondUserName: String): MutableList<MessageEntity>
        fun saveMessage(message: MessageEntity)
        fun getLastMessage(chatID : Int) : MessageEntity?
        fun deleteAllMessagesBetween(clientNickName: String, friendNickName: String)

        fun insertChat(chat: ChatEntity) : Long
        fun getOrderedAndLimitedChatEntities(clientNickName : String, index : Int, searchText: String) : List<ChatEntity>
    }
}