package com.example.httpserver.database.chat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.httpserver.database.message.MessageEntity

@Dao
interface ChatDao {

//    @Query("""
//        select chat.id
//        from chat_table chat
//        where chat.id in (
//            select ch.id from chat_table ch
//            left join message_table m
//            on m.chatID = ch.id
//            where ((ch.firstUser = :clientNickName and ch.secondUser like :searchText) or (ch.secondUser = :clientNickName and ch.firstUser like :searchText))
//            and ((len(:searchText) > 2) or m.sendTime != null)
//        )
//
//        """)
//    fun getHistory(clientNickName : String, index : Int, searchText: String)

    @Query("""
        select ch.id from chat_table ch
        left join message_table m
        on m.chatID = ch.id
        where ((ch.firstUser = :clientNickName and ch.secondUser like :searchText) or (ch.secondUser = :clientNickName and ch.firstUser like :searchText))
        and ((len(:searchText) > 2) or m.sendTime != null)
        group by ch.id
        order by CASE WHEN m.sendTime IS NULL THEN 1 ELSE 0 END, m.sendTime DESC
        limit 10
        offset :index
        """)
    fun getHistory(clientNickName : String, index : Int, searchText: String) : List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChat(chat: ChatEntity) : Long

}