package com.example.chatclient.pages.historypage

import com.example.chatclient.network.api.dataClasses.HistoryItem

interface HistoryPageContract {

    interface View {
        fun updateData(newData : List<HistoryItem>, searchString: String)
    }

    interface Presenter {
        fun getData(searchText : String, index: Int)
        fun removeMessages(nickname: String)
        fun updateData(newData : List<HistoryItem>, searchString: String)
    }

    interface Model {
        fun getData(searchText : String, index: Int)
        fun removeMessages(nickname: String)
    }

}