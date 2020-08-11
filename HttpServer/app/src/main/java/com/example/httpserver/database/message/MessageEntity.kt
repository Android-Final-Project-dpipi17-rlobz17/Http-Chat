package com.example.httpserver.database.message

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.httpserver.database.chat.ChatEntity
import com.example.httpserver.database.user.UserEntity
import java.util.*

@Entity(tableName = "message_table",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["nickname"],
            childColumns = ["fromNickName"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["nickname"],
            childColumns = ["toNickName"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = ChatEntity::class,
            parentColumns = ["id"],
            childColumns = ["chatID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromNickName: String,
    val toNickName: String,
    val text: String,
    val sendTime: Date,
    val chatID: Int
)