package com.example.chatclient.pages.historypage.recyclerView

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.network.dataclasses.HistoryResponse
import com.example.chatclient.utils.DateUtils
import com.example.chatclient.utils.ImageUtils

class HistoryRecyclerViewViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var history_recycler_view_item_image_view : ImageView? = null
    private var history_recycler_view_item_nickname_text_view : TextView? = null
    private var history_recycler_view_item_last_message_text_view : TextView? = null
    private var history_recycler_view_item_last_date_text_view : TextView? = null

    init {
        history_recycler_view_item_image_view = itemView.findViewById(R.id.history_recycler_view_item_image_view)
        history_recycler_view_item_nickname_text_view = itemView.findViewById(R.id.history_recycler_view_item_nickname_text_view)
        history_recycler_view_item_last_message_text_view = itemView.findViewById(R.id.history_recycler_view_item_last_message_text_view)
        history_recycler_view_item_last_date_text_view = itemView.findViewById(R.id.history_recycler_view_item_last_date_text_view)
    }

    fun setUpView(model: HistoryResponse){
        if(model.friend_image_string == ""){
            history_recycler_view_item_image_view?.setImageResource(R.drawable.login_default_avatar)
        }else {
            history_recycler_view_item_image_view?.setImageBitmap(ImageUtils.base64ToBitMap(model.friend_image_string))
        }

        history_recycler_view_item_nickname_text_view?.text = model.friend_nickname
        history_recycler_view_item_last_message_text_view?.text = model.friend_last_message

        model.friend_last_date?.let {
            history_recycler_view_item_last_date_text_view?.text = DateUtils.formatDate(it)
        }
    }

}