package com.example.httpserver.database.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.httpserver.database.user.UserEntity

@Dao
interface MessageDao {

    @Query("""
        select * from message_table m
        where (m.fromNickName = :firstUserName and m.toNickName = :secondUserName)
        or (m.fromNickName = :secondUserName and m.toNickName = :firstUserName)
        order by m.sendTime desc
        """)
    fun getChatMessages(firstUserName: String, secondUserName: String): MutableList<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: MessageEntity) : Long

    @Query("select * from message_table")
    fun getAllMessages(): List<MessageEntity>
}
