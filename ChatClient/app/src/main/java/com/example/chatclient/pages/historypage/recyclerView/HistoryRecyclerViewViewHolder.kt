package com.example.chatclient.pages.historypage.recyclerView

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R

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

    fun setUpView(model: HistoryRecyclerViewCellModel){
        if(model.image_string == ""){
            history_recycler_view_item_image_view?.setImageResource(R.drawable.login_default_avatar)
        }else {
            val imageBytes =
                android.util.Base64.decode(model.image_string, android.util.Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            history_recycler_view_item_image_view?.setImageBitmap(decodedImage)
        }

        history_recycler_view_item_nickname_text_view?.text = model.nickname
        history_recycler_view_item_last_message_text_view?.text = model.last_message
        // TODO[RL] display date better
        history_recycler_view_item_last_date_text_view?.text = model.last_date.toString()
    }

}