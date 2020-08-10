package com.example.chatclient.pages.historypage.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R

class HistoryRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var data : List<HistoryRecyclerViewCellModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_recycler_view_item, parent, false)
        return HistoryRecyclerViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val history_recycler_view_cell_view_holder = holder as HistoryRecyclerViewViewHolder
        history_recycler_view_cell_view_holder.setUpView(data[position])
    }

    fun updateData(newData: List<HistoryRecyclerViewCellModel>){
        data = newData
        notifyDataSetChanged()
    }

}