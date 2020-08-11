package com.example.httpserver

import android.content.Context
import androidx.room.Room
import com.example.httpserver.database.MessageServiceDatabase
import com.example.httpserver.database.chat.ChatEntity
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.user.UserEntity

class ServerModelImpl(var presenter: ServerContract.Presenter, var context: Context) : ServerContract.Model {

    private var database: MessageServiceDatabase =
        Room.databaseBuilder(context, MessageServiceDatabase::class.java, "notes_database")
        .fallbackToDestructiveMigration()
        .build()

    override fun getAllUsers(exceptUserNickName: String): List<UserEntity> {
        return database.getUserDao().getAllUsers(exceptUserNickName)
    }

    override fun getUserByNickName(nickName: String): UserEntity? {
        return database.getUserDao().getUserByNickName(nickName)
    }

    override fun saveUser(user: UserEntity) {
        database.getUserDao().saveUser(user)
    }

    override fun updateUser(user: UserEntity) {
        database.getUserDao().updateUser(user)
    }

    override fun getChatMessages(firstUserName: String, secondUserName: String): MutableList<MessageEntity> {
        return database.getMessageDao().getChatMessages(firstUserName, secondUserName)
    }

    override fun saveMessage(message: MessageEntity) {
        database.getMessageDao().saveMessage(message)
    }

    override fun getLastMessage(chatID: Int): MessageEntity? {
        return database.getMessageDao().getLastMessage(chatID)
    }

    override fun deleteAllMessagesBetween(clientNickName: String, friendNickName: String) {
        database.getMessageDao().deleteAllMessagesBetween(clientNickName, friendNickName)
    }

    override fun insertChat(chat: ChatEntity): Long {
        return database.getChatDao().insertChat(chat)
    }

    override fun getOrderedAndLimitedChatEntities(
        clientNickName: String,
        index: Int,
        searchText: String
    ): List<ChatEntity> {
        return database.getChatDao().getOrderedAndLimitedChatEntities(clientNickName, index, "%${searchText}%")
    }

    override fun forDebug() {
        var users = database.getUserDao().getAllUsers("")
        var messages = database.getMessageDao().getAllMessages()
        var chats = database.getChatDao().getAllChats()

        var m = 2 + 2
        var n = m + 1
    }


}