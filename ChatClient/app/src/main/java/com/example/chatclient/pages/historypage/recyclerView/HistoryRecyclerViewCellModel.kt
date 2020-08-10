package com.example.chatclient.pages.historypage.recyclerView

import java.util.*

data class HistoryRecyclerViewCellModel(
    var image_string : String,
    var nickname : String,
    var last_message: String,
    var last_date: Date
)