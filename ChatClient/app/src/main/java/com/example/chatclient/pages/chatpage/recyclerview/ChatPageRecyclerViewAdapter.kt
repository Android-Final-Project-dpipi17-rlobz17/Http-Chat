package com.example.chatclient.pages.chatpage.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R

class ChatPageRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        const val MY_MESSAGE = 1
        const val FRIEND_MESSAGE = 2
    }

    private var cells : List<MessageCellModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val cellView: View = if (viewType == MY_MESSAGE) {
            LayoutInflater.from(parent.context).inflate(R.layout.my_message_cell, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.friend_message_cell, parent, false)
        }
        return MessageCellViewHolder(cellView)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val forecastCellViewHolder: MessageCellViewHolder = holder as MessageCellViewHolder
        forecastCellViewHolder.setUpView(cells[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (cells[position].isMyMessage) MY_MESSAGE else FRIEND_MESSAGE
    }

    fun setUpCells(cells: List<MessageCellModel>) {
        this.cells = cells
        notifyDataSetChanged()
    }

    fun addMessage(messageCellModel: MessageCellModel) {
        cells += (messageCellModel)
        notifyItemInserted(cells.size - 1)
    }
}