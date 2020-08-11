package com.example.httpserver.database.chat

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "chat_table")
data class ChatEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var firstUser : String,
    var secondUser : String
)