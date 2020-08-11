package com.example.httpserver

import android.content.Context
import androidx.room.Room
import com.example.httpserver.database.MessageServiceDatabase
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.user.UserEntity

class ServerModelImpl(var presenter: ServerContract.Presenter, var context: Context) : ServerContract.Model {

    private var database: MessageServiceDatabase =
        Room.databaseBuilder(context, MessageServiceDatabase::class.java, "notes_database")
        .fallbackToDestructiveMigration()
        .build()

    override fun getUserByNickName(nickName: String): UserEntity? {
        return database.getUserDao().getUserByNickName(nickName)
    }

    override fun saveUser(user: UserEntity) {
        database.getUserDao().saveUser(user)
    }

    override fun getChatMessages(firstUserName: String, secondUserName: String): MutableList<MessageEntity> {
        return database.getMessageDao().getChatMessages(firstUserName, secondUserName)
    }

    override fun saveMessage(message: MessageEntity) {
        //TODO[DP] uncomment later
        //database.getMessageDao().saveMessage(message)
    }


}