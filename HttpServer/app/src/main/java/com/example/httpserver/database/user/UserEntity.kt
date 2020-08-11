package com.example.httpserver.database.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    var nickname : String,
    var about : String,
    var profile_picture : String
)