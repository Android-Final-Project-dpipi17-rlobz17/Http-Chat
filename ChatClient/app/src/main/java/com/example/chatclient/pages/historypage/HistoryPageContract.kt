package com.example.chatclient.pages.historypage

import android.content.Context
import com.example.chatclient.network.dataclasses.HistoryResponse

interface HistoryPageContract {

    interface View {
        fun updateData(newData : List<HistoryResponse>, searchString: String)
        fun getContext() : Context
    }

    interface Presenter {
        fun getData(searchText : String, index: Int)
        fun removeMessages(nickname: String)
        fun updateData(newData : List<HistoryResponse>, searchString: String)
    }

    interface Model {
        fun getData(userNickname: String, searchText : String, index: Int)
        fun removeMessages(nickname: String)
    }

}