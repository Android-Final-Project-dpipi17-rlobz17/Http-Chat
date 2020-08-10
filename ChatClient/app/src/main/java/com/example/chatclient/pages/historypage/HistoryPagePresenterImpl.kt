package com.example.chatclient.pages.historypage

import android.content.Context

class HistoryPagePresenterImpl (var view: HistoryPageContract.View, var context: Context) : HistoryPageContract.Presenter{

    override fun getData(searchText: String) {
        TODO("Not yet implemented")
    }

    override fun removeMessages(nickname: String) {
        TODO("Not yet implemented")
    }

}