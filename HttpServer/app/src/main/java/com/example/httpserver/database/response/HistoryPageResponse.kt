package com.example.httpserver.database.response

import java.util.*

data class HistoryPageResponse (
    val chat_id: Int,
    val friend_image_string : String,
    val friend_nickname : String,
    val friend_last_message : String,
    val friend_last_date: Date
)