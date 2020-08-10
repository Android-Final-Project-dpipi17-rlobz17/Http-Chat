package com.example.chatclient.network.api.dataClasses

import java.util.*

data class HistoryItem (
    val image_string : String,
    val nickname : String,
    val last_message : String,
    val last_date: Date
)