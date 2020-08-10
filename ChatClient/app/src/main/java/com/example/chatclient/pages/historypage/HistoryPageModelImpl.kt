package com.example.chatclient.pages.historypage

import java.util.*


class HistoryPageModelImpl (var presenter: HistoryPageContract.Presenter) : HistoryPageContract.Model{

    override fun getData(searchText: String): List<Objects> {
        TODO("Not yet implemented")
    }

    override fun removeMessages(nickname: String) {
        TODO("Not yet implemented")
    }

}