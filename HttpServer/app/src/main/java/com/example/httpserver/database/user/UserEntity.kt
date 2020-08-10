package com.example.httpserver.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val nickName: String,
    val profession: String,
    val photo: String
)