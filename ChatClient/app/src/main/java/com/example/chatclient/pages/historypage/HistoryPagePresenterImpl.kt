package com.example.chatclient.pages.historypage

import android.content.Context
import com.example.chatclient.SharedPreferencesInfo
import com.example.chatclient.network.dataclasses.HistoryResponse

class HistoryPagePresenterImpl (var view: HistoryPageContract.View, var context: Context) : HistoryPageContract.Presenter{

    private var model = HistoryPageModelImpl(this)

    override fun addNewDataLazyLoading(searchText: String, index: Int) {
        val sharedPreferences = view.getSavedContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        val userNickname = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        model.addNewDataLazyLoading(userNickname, searchText, index)
    }

    override fun changeData(searchText: String) {
        val sharedPreferences = view.getSavedContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        val userNickname = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        model.changeData(userNickname, searchText)
    }

    override fun removeMessages(friendNickname: String) {
        val sharedPreferences = view.getSavedContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        val userNickname = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        model.removeMessages(userNickname, friendNickname)
    }

    override fun checkData(searchText: String) {
        val sharedPreferences = view.getSavedContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        val userNickname = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        model.checkData(userNickname, searchText)
    }

    override fun newDataForLazyLoading(newData: List<HistoryResponse>) {
        view.newDataForLazyLoading(newData)
    }

    override fun newDataForChange(newData: List<HistoryResponse>) {
        view.newDataForChange(newData)
    }

    override fun dataNeedsUpdating() {
        view.dataNeedsUpdating()
    }

    override fun checkIfDataNeedsUpdating(newData : List<HistoryResponse>) {
        view.checkIfDataNeedsUpdating(newData)
    }

}