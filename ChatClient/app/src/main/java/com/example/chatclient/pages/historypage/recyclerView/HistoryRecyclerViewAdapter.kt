package com.example.chatclient.pages.historypage.recyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R

class HistoryRecyclerViewAdapter(private val navController: NavController) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var data : List<HistoryRecyclerViewCellModel>

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
    }

    fun updateData(newData: List<HistoryRecyclerViewCellModel>){
        data = newData
        notifyDataSetChanged()
    }

}