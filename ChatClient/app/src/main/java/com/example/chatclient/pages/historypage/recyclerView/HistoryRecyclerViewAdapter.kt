package com.example.chatclient.pages.historypage.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.network.dataclasses.HistoryResponse
import com.example.chatclient.pages.historypage.HistoryPagePresenterImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryRecyclerViewAdapter(val presenter : HistoryPagePresenterImpl, private val navController: NavController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data : List<HistoryResponse> = listOf()
    private lateinit var onGetDataHandler: OnGetDataHandler

    private var clickListener = View.OnClickListener {
        val args = Bundle()
        args.putString("friend nickname", data[it.tag as Int].friend_nickname)
        navController.navigate(R.id.action_historyPageFragment_to_chatPageFragment, args)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_recycler_view_item, parent, false)
        view.setOnClickListener(clickListener)
        return HistoryRecyclerViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val historyRecyclerViewViewHolder = holder as HistoryRecyclerViewViewHolder
        historyRecyclerViewViewHolder.setUpView(data[position])
        historyRecyclerViewViewHolder.itemView.tag = historyRecyclerViewViewHolder.adapterPosition

        if(position % 10 == 0) {
            //onGetDataHandler.onGetData(position)
        }
    }

    fun setUpView(onGetDataHandler: OnGetDataHandler) {
        this.onGetDataHandler = onGetDataHandler
    }

    fun updateData(newData: List<HistoryResponse>){
        data = newData
        notifyDataSetChanged()
    }

    interface OnGetDataHandler {
        fun onGetData(position: Int)
    }

}