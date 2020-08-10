<<<<<<< HEAD
//package com.example.httpserver.database
//
//import androidx.room.Database
//import androidx.room.TypeConverters
//import com.example.httpserver.database.message.MessageEntity
//import com.example.httpserver.database.message.MessageDao
//import com.example.httpserver.database.user.UserDao
//import com.example.httpserver.database.user.UserEntity
//
//@Database(entities = [MessageEntity::class, UserEntity::class], version = 0)
//@TypeConverters(Converters::class)
//abstract class MessageServiceDatabase {
//    abstract fun getMessageDao(): MessageDao
//    abstract fun getUserDao(): UserDao
//}
=======
package com.example.httpserver.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.message.MessageDao
import com.example.httpserver.database.user.UserDao
import com.example.httpserver.database.user.UserEntity

@Database(entities = [MessageEntity::class, UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class MessageServiceDatabase : RoomDatabase() {
    abstract fun getMessageDao(): MessageDao
    abstract fun getUserDao(): UserDao
}
>>>>>>> 8f0a334d4582ef584f8722a78fa47ce8b68e7680
