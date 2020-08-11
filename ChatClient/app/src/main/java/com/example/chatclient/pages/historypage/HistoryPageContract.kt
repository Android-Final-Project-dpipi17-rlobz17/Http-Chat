package com.example.chatclient.pages.historypage

import android.content.Context
import com.example.chatclient.network.dataclasses.HistoryResponse

interface HistoryPageContract {

    interface View {
        // called by presenter
        fun newDataForLazyLoading(newData : List<HistoryResponse>)
        fun newDataForChange(newData : List<HistoryResponse>)
        fun getSavedContext() : Context
        fun dataNeedsUpdating()
        fun checkIfDataNeedsUpdating(newData : List<HistoryResponse>)
    }

    interface Presenter {
        // called by view and calls model
        fun addNewDataLazyLoading(searchText : String, index: Int)
        fun changeData(searchText : String)
        fun removeMessages(friendNickname: String)
        fun checkData(searchText : String)

        // called by model and calls view
        fun newDataForLazyLoading(newData : List<HistoryResponse>)
        fun newDataForChange(newData : List<HistoryResponse>)
        fun dataNeedsUpdating()
        fun checkIfDataNeedsUpdating(newData : List<HistoryResponse>)
    }

    interface Model {
        // called by presenter
        fun addNewDataLazyLoading(userNickname: String, searchText : String, index: Int)
        fun changeData(userNickname: String, searchText : String)
        fun removeMessages(userNickname: String, friendNickname: String)
        fun checkData(userNickname: String,searchText : String)
    }

}