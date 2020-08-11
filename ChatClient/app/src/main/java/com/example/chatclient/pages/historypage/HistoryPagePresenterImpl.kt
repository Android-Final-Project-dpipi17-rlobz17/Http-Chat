package com.example.chatclient.pages.historypage

import android.content.Context
import com.example.chatclient.SharedPreferencesInfo
import com.example.chatclient.network.dataclasses.HistoryResponse

class HistoryPagePresenterImpl (var view: HistoryPageContract.View, var context: Context) : HistoryPageContract.Presenter{

    private var model = HistoryPageModelImpl(this)

    override fun getData(searchText: String, index: Int) {

        val sharedPreferences = view.getContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        var userNickname = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        model.getData(userNickname, searchText, index)
    }

    override fun removeMessages(friendNickname: String) {
        val sharedPreferences = view.getContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        var userNickname = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        model.removeMessages(userNickname, friendNickname)
    }

    override fun updateData(newData: List<HistoryResponse>, searchString: String) {
        view.updateData(newData, searchString)
    }

    override fun dataChanged() {
        view.dataChanged()
    }
}