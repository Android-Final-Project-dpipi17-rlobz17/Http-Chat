package com.example.chatclient.pages.chatpage.customviews

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chatclient.R

class ChatPageMessageInput(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs) {

    private var messageEditText: EditText
    private var sendButton: ImageView

    init {
        inflate(context, R.layout.chat_page_message_input, this)

        messageEditText = findViewById(R.id.char_page_message_input_edit_text)
        sendButton = findViewById(R.id.chat_page_message_input_send_button)
    }

    fun setUpView(sendButtonClickHandler: SendButtonClickHandler) {
        messageEditText.setOnClickListener {
            sendButtonClickHandler.onEditTextClick()
        }

        sendButton.setOnClickListener {
            sendButtonClickHandler.onSendButtonClickHandler(messageEditText.text.toString())
            messageEditText.text.clear()
        }
    }

    interface SendButtonClickHandler {
        fun onEditTextClick()
        fun onSendButtonClickHandler(message: String)
    }

}