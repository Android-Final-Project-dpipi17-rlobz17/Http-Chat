package com.example.chatclient.pages.historypage

import java.util.*

interface HistoryPageContract {

    interface View {
        fun onDataUpdate()
    }

    interface Presenter {
        fun getData(searchText : String)
        fun removeMessages(nickname: String)
    }

    interface Model {
        fun getData(searchText : String) : List<Objects>
        fun removeMessages(nickname: String)
    }

}