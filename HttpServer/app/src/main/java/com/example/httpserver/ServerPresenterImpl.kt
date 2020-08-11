package com.example.httpserver

import android.content.Context
import com.example.httpserver.database.message.MessageEntity
import com.example.httpserver.database.response.ChatPageResponse
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

class ServerPresenterImpl(var view: ServerContract.View, var context: Context) : ServerContract.Presenter {

    private var model: ServerContract.Model = ServerModelImpl(this, context)
    private var mHttpServer: HttpServer? = null

    override fun startServer() {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0)
            mHttpServer!!.executor = Executors.newCachedThreadPool()

            mHttpServer!!.createContext("/connection", connectionHandler)
            mHttpServer!!.createContext("/messages", messageHandler)
            mHttpServer!!.createContext("/history", historyHandler)
            mHttpServer!!.createContext("/login", loginHandler)

            mHttpServer!!.start()

            view.onServerStart()
        } catch (e: IOException) {
            e.printStackTrace()
            view.onServerStop()
        }
    }

    override fun stopServer() {
        if (mHttpServer != null){
            mHttpServer!!.stop(0)
            view.onServerStop()
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
                sendResponse(exchange, ChatPageResponse(it, messages).toString())
            } else {
                sendResponse(exchange, ChatPageResponse(it, ArrayList()).toString())
            }
        }
    }

    private fun handleSendMessage(exchange: HttpExchange) {
        val inputStreamReader = InputStreamReader(exchange.requestBody, "utf-8")
        val jsonString = BufferedReader(inputStreamReader).use(BufferedReader::readText)
        val message: MessageEntity = Gson().fromJson(jsonString, MessageEntity::class.java)

        model.saveMessage(message)
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
        }else{
            if(user.profile_picture.isEmpty()){
                user.profile_picture = userFromDatabase.profile_picture
            }
            model.saveUser(user)
        }
    }

    private val historyHandler = HttpHandler {exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    handleHistoryData(exchange)
                }
                "POST" -> {
                    handleLogin(exchange)
                }
            }
        }
    }

    private fun handleHistoryData(exchange: HttpExchange){

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


}