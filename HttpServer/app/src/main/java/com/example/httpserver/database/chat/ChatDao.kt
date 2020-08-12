package com.example.httpserver.database.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {

    @Query("""
        select ch.* from chat_table ch
        left join message_table m
        on m.chatID = ch.id
        where ((ch.firstUser = :clientNickName and ch.secondUser like :searchText) or (ch.secondUser = :clientNickName and ch.firstUser like :searchText))
        and ((length(:searchText) > 2) or m.sendTime is not null)
        group by ch.id
        order by CASE WHEN m.sendTime IS NULL THEN 0 ELSE 1 END Desc, max(m.sendTime) Desc
        limit 10
        offset :index
        """)
    fun getOrderedAndLimitedChatEntities(clientNickName : String, index : Int, searchText: String) : List<ChatEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat: ChatEntity) : Long

    @Query("select * from chat_table")
    fun getAllChats(): List<ChatEntity>

}