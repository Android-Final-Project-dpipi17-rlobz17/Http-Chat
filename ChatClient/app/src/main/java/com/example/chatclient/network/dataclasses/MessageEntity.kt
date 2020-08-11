package com.example.chatclient.network.dataclasses

import java.util.*

data class MessageEntity (
    val id: Int,
    val fromNickName: String,
    val toNickName: String,
    val text: String,
    val sendTime: Date,
    val chatID: Int
)

