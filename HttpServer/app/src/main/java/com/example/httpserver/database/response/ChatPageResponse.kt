package com.example.httpserver.database.response

import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.user.UserEntity

data class ChatPageResponse (
     var friendUser: UserEntity,
     var messages: MutableList<MessageEntity>
)
