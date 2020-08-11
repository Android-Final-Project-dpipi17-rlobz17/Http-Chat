package com.example.httpserver.database.message

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("""
        select * from message_table m
        where (m.fromNickName = :firstUserName and m.toNickName = :secondUserName)
        or (m.fromNickName = :secondUserName and m.toNickName = :firstUserName)
        order by m.sendTime desc
        """)
    fun getChatMessages(firstUserName: String, secondUserName: String): MutableList<MessageEntity>

//    @Query("""
//        select m.text, m.sendTime from message_table m
//        where m.fromNickName = :clientNickName
//        """)
//    fun getHistory(clientNickName : String, index : Int, searchText: String)

//    @Query("""
//        select * from user_table u
//        where (u.nickname in (
//            select m.toNickName from message_table m
//            where (m.fromNickName = :clientNickName)
//        ))
//
//
//
//        """)
//    fun getHistory(clientNickName : String, index : Int, searchText: String)

//    @Query("""
//        select us.*
//        from user_table us
//        where
//            (us.nickname in (
//                select m.fromNickName from message_table m
//                where m.toNickName = :clientNickName
//            )
//            or us.nickname in (
//                select m.toNickName from message_table m
//                where m.fromNickName = :clientNickName
//            )
//        ) and us.nickname like :searchText
//        """)
//    fun getHistory(clientNickName : String, index : Int, searchText: String)
//


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMessage(message: MessageEntity) : Long
}