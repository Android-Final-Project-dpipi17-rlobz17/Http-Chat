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

    @Query("""
        select m.* from message_table m
        where (m.chatID = :chatID)
        order by m.sendTime desc
        limit 1
        """)
    fun getLastMessage(chatID: Int): MessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: MessageEntity) : Long

    @Query("select * from message_table")
    fun getAllMessages(): List<MessageEntity>


    @Query ("""
        Delete from message_table
        where chatID in 
            (select ch.id from chat_table ch where 
                (ch.firstUser = :clientNickName and ch.secondUser = :friendNickName) or
                (ch.secondUser = :clientNickName and ch.firstUser = :friendNickName))
                
    """)
    fun deleteAllMessagesBetween(clientNickName: String, friendNickName: String)
}
