package com.example.chatclient.pages.chatpage.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chatclient.R

class ChatPageMessageInput(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.chat_page_message_input, this)
    }
}