package com.example.chatclient.pages.chatpage.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R

class MessageCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var messageText : TextView? = null

    init {
        messageText = itemView.findViewById(R.id.message_cell_text)
    }

    fun setUpView(model : MessageCellModel) {
        messageText?.text = model.text
    }
}