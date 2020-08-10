package com.example.chatclient.pages.historypage.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.network.api.dataClasses.HistoryItem
import com.example.chatclient.pages.historypage.HistoryPagePresenterImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryRecyclerViewAdapter(val presenter : HistoryPagePresenterImpl, private val navController: NavController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data : List<HistoryItem> = listOf()
    private var searchString: String = ""

    private var clickListener = View.OnClickListener {
        val args = Bundle()
        args.putString("friend nickname", data[it.tag as Int].nickname)
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
            GlobalScope.launch {
                presenter.getData(searchString, position)
            }
        }
    }

    fun updateData(newData: List<HistoryItem>, searchString: String){
        this.searchString = searchString
        data = newData
        notifyDataSetChanged()
    }

}