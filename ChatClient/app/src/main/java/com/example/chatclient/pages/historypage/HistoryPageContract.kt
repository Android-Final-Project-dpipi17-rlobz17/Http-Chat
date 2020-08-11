package com.example.chatclient.pages.historypage

import android.content.Context
import com.example.chatclient.network.dataclasses.HistoryResponse

interface HistoryPageContract {

    interface View {
        fun updateData(newData : List<HistoryResponse>, searchString: String)
        fun dataChanged()
        fun getContext() : Context
    }

    interface Presenter {
        fun getData(searchText : String, index: Int)
        fun removeMessages(friendNickname: String)
        fun updateData(newData : List<HistoryResponse>, searchString: String)
        fun dataChanged()
    }

    interface Model {
        fun getData(userNickname: String, searchText : String, index: Int)
        fun removeMessages(userNickname: String, friendNickname: String)
    }

}