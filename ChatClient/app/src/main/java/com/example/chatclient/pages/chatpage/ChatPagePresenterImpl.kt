package com.example.chatclient.pages.chatpage

import android.content.Context
import com.example.chatclient.network.dataclasses.MessageEntity
import com.example.chatclient.pages.chatpage.customviews.CollapsibleToolBarLayoutModel
import com.example.chatclient.pages.chatpage.recyclerview.MessageCellModel
import com.example.chatclient.pages.chatpage.responsedataclasses.ChatPageResponse
import java.util.*
import kotlin.collections.ArrayList

class ChatPagePresenterImpl(var view: ChatPageContract.View, var context: Context) : ChatPageContract.Presenter {

    private var model: ChatPageContract.Model = ChatPageModelImpl(this)

    override fun fetchMessages(myNickName: String, friendNickName: String) {
        model.fetchMessages(myNickName, friendNickName)
    }

    override fun sendMessage(from: String, to: String, text: String) {
        model.sendMessage(MessageEntity(0, from, to, text, Date()))
    }

    override fun onMessageFetch(chatPageResponse: ChatPageResponse) {
        view.setUpToolBar(
            CollapsibleToolBarLayoutModel(
                chatPageResponse.friendUser.nickname,
                chatPageResponse.friendUser.about,
                chatPageResponse.friendUser.profile_picture
            )
        )

        var messageCellModels: List<MessageCellModel> = ArrayList()
        chatPageResponse.messages.forEach {
            messageCellModels += (
                MessageCellModel(
                    it.text,
                    it.fromNickName != chatPageResponse.friendUser.nickname,
                    it.sendTime
                )
            )
        }
        view.updateRecyclerView(messageCellModels)
    }
}