package com.example.chatclient.pages.chatpage.responsedataclasses

import com.example.chatclient.network.dataclasses.MessageEntity
import com.example.chatclient.network.dataclasses.UserEntity

data class ChatPageResponse(
    var friendUser: UserEntity,
    var messages: MutableList<MessageEntity>
)