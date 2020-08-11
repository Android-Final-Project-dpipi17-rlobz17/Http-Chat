package com.example.chatclient.pages.chatpage.recyclerview

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R

class MessageCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var messageText : TextView
    private var sentTimeText : TextView

    init {
        messageText = itemView.findViewById(R.id.message_cell_text)
        sentTimeText = itemView.findViewById(R.id.message_time_tw)
    }

    fun setUpView(model : MessageCellModel) {
        messageText.text = model.text
        sentTimeText.text = model.time.toString()
    }
}