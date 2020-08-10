package com.example.chatclient.pages.historypage

import android.content.Context
import com.example.chatclient.network.api.dataClasses.HistoryItem

class HistoryPagePresenterImpl (var view: HistoryPageContract.View, var context: Context) : HistoryPageContract.Presenter{

    private var model = HistoryPageModelImpl(this)

    override fun getData(searchText: String, index: Int) {
        model.getData(searchText, index)
    }

    override fun removeMessages(nickname: String) {
        model.removeMessages(nickname)
    }

    override fun updateData(newData: List<HistoryItem>, searchString: String) {
        view.updateData(newData, searchString)
    }
}