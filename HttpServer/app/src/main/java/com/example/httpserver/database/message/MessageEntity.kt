package com.example.httpserver.database.message

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.httpserver.database.user.UserEntity
import java.util.*

@Entity(tableName = "message_table",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["nickName"],
            childColumns = ["fromNickName"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["nickName"],
            childColumns = ["toNickName"],
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
    val sendTime: Date
)