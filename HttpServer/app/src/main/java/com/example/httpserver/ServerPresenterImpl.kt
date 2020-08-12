package com.example.httpserver

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.httpserver.database.chat.ChatEntity
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.response.ChatPageResponse
import com.example.httpserver.database.response.HistoryPageResponse
import com.example.httpserver.database.user.UserEntity
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class ServerPresenterImpl : ServerContract.Presenter, Service() {

    private lateinit var model: ServerContract.Model
    private var mHttpServer: HttpServer? = null

    override fun onCreate() {
        super.onCreate()
        model = ServerModelImpl(this, applicationContext)
        startServer()
    }

    override fun startServer() {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0)
            mHttpServer!!.executor = Executors.newCachedThreadPool()

            mHttpServer!!.createContext("/connection", connectionHandler)
            mHttpServer!!.createContext("/messages", messageHandler)
            mHttpServer!!.createContext("/history", historyHandler)
            mHttpServer!!.createContext("/login", loginHandler)

            mHttpServer!!.start()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun stopServer() {
        if (mHttpServer != null){
            mHttpServer!!.stop(0)
        }
    }

    override fun sendResponse(httpExchange: HttpExchange, responseText: String) {
        httpExchange.sendResponseHeaders(200, responseText.length.toLong())
        val os = httpExchange.responseBody
        os.write(responseText.toByteArray())
        os.close()
    }

    private val messageHandler = HttpHandler { httpExchange ->
        run {
            when (httpExchange!!.requestMethod) {
                "GET" -> {
                    handleGetMessages(httpExchange)
                }

                "POST" -> {
                    handleSendMessage(httpExchange)
                }

            }
        }
    }

    private fun handleGetMessages(exchange: HttpExchange) {
        val params = queryToMap(exchange.requestURI.rawQuery)
        val clientNickName = params?.get("clientNickName")
        val friendNickName = params?.get("friendNickName")

        val friendUser = friendNickName?.let { model.getUserByNickName(it) }

        friendUser?.let {
            if (clientNickName != null) {
                val messages = model.getChatMessages(clientNickName, friendNickName)
                sendResponse(exchange, Gson().toJson(ChatPageResponse(it, messages)))
            } else {
                sendResponse(exchange, Gson().toJson(ChatPageResponse(it, ArrayList())))
            }
        }
    }

    private fun handleSendMessage(exchange: HttpExchange) {
        val inputStreamReader = InputStreamReader(exchange.requestBody, "utf-8")
        val jsonString = BufferedReader(inputStreamReader).use(BufferedReader::readText)
        val message: MessageEntity = Gson().fromJson(jsonString, MessageEntity::class.java)

        model.saveMessage(message)
        sendResponse(exchange, true.toString())
    }

    private val connectionHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    sendResponse(exchange, true.toString())
                }
            }
        }
    }

    private val loginHandler = HttpHandler {exchange ->
        run {
            when (exchange!!.requestMethod) {
                "POST" -> {
                    handleLogin(exchange)
                }
            }
        }
    }

    private fun handleLogin(exchange: HttpExchange){
        val inputStreamReader = InputStreamReader(exchange.requestBody, "utf-8")
        val jsonString = BufferedReader(inputStreamReader).use(BufferedReader::readText)
        val user: UserEntity = Gson().fromJson(jsonString, UserEntity::class.java)

        val userFromDatabase = model.getUserByNickName(user.nickname)

        if(userFromDatabase == null){
            model.saveUser(user)
            var otherUsers = model.getAllUsers(user.nickname)
            otherUsers.forEach {
                model.insertChat(ChatEntity(0, user.nickname, it.nickname))
            }
        }else{
            if(user.profile_picture.isEmpty()){
                user.profile_picture = userFromDatabase.profile_picture
            }
            model.updateUser(user)
        }
        sendResponse(exchange, true.toString())
    }

    private val historyHandler = HttpHandler {exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    handleHistoryData(exchange)
                }
                "POST" -> {
                    handleHistoryRemove(exchange)
                }
            }
        }
    }

    private fun handleHistoryData(exchange: HttpExchange){
        val params = queryToMap(exchange.requestURI.rawQuery)
        val clientNickName = params?.get("clientNickName")
        val index = params?.get("index")?.toInt()
        val searchText = params?.get("searchText")

        if(clientNickName != null && index != null && searchText != null){
            val chatEntityList = model.getOrderedAndLimitedChatEntities(clientNickName, index, searchText)
            val resultList = mutableListOf<HistoryPageResponse>()
            for (chatEntity in chatEntityList){
                var friendNickName = chatEntity.firstUser
                if(chatEntity.firstUser == clientNickName){
                    friendNickName = chatEntity.secondUser
                }
                val friendUserEntity = model.getUserByNickName(friendNickName)!!
                val lastMessage = model.getLastMessage(chatEntity.id)

                var lastMessageText = ""
                var lastMessageDate : Date? = null
                if(lastMessage != null){
                    lastMessageText = lastMessage.text
                    lastMessageDate = lastMessage.sendTime
                }

                resultList.add(HistoryPageResponse(chatEntity.id,friendUserEntity.profile_picture, friendUserEntity.nickname, lastMessageText, lastMessageDate))
            }
            sendResponse(exchange, Gson().toJson(resultList))
        }
    }

    private fun handleHistoryRemove(exchange: HttpExchange){
        val params = queryToMap(exchange.requestURI.rawQuery)
        val clientNickname = params?.get("clientNickName")
        val friendNickName = params?.get("friendNickName")

        if(clientNickname != null && friendNickName != null){
            model.deleteAllMessagesBetween(clientNickname, friendNickName)
        }

        sendResponse(exchange, true.toString())
    }


    companion object {
        const val port = 5000

        fun queryToMap(query: String): Map<String, String>? {
            val result: MutableMap<String, String> = HashMap()

            for (param in query.split("&".toRegex()).toTypedArray()) {
                val entry = param.split("=".toRegex()).toTypedArray()
                if (entry.size > 1) {
                    result[entry[0]] = entry[1]
                } else {
                    result[entry[0]] = ""
                }
            }
            return result
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}