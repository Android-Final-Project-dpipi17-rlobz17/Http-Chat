package com.example.chatclient.network.dataclasses

import java.util.*

data class HistoryResponse (
    val chat_id: Int,
    val friend_image_string : String,
    val friend_nickname : String,
    val friend_last_message : String,
    val friend_last_date: Date?
)