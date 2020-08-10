package com.example.httpserver.database.message

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.httpserver.database.user.UserEntity
import java.util.*

@Entity(tableName = "message_table",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["fromUserId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["toUserId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MessageEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromUserId: Int,
    val toUserId: Int,
    val text: String,
    val sendTime: Date
)