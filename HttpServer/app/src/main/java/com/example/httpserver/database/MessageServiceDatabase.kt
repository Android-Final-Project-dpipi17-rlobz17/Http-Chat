package com.example.httpserver.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.httpserver.database.chat.ChatDao
import com.example.httpserver.database.chat.ChatEntity
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.message.MessageDao
import com.example.httpserver.database.user.UserDao
import com.example.httpserver.database.user.UserEntity

@Database(entities = [MessageEntity::class, UserEntity::class, ChatEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class MessageServiceDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao
    abstract fun getUserDao(): UserDao
    abstract fun getChatDao(): ChatDao
}

