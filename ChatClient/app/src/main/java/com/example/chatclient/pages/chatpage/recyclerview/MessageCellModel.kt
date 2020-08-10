package com.example.chatclient.pages.chatpage.recyclerview

import java.util.*

data class MessageCellModel (
    var text: String,
    var isMyMessage: Boolean,
    var time: Date
)