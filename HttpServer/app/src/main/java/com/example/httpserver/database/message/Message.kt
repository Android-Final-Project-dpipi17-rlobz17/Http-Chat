package com.example.httpserver.database.message

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.httpserver.database.user.User
import java.util.*

@Entity(tableName = "message_table",
    foreignKeys = [
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["fromUserId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(entity = User::class,
            parentColumns = ["id"],
            childColumns = ["toUserId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Message (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromUserId: Int,
    val toUserId: Int,
    val text: String,
    val sendTime: Date
)