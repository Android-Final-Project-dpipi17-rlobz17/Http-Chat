package com.example.httpserver

import android.content.Context
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.*
import java.util.concurrent.Executors

class ServerPresenterImpl(var view: ServerContract.View, var context: Context) : ServerContract.Presenter {

    private var model: ServerContract.Model = ServerModelImpl(this, context)
    private var mHttpServer: HttpServer? = null

    override fun startServer() {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0)
            mHttpServer!!.executor = Executors.newCachedThreadPool()

            mHttpServer!!.createContext("/", rootHandler)
            mHttpServer!!.createContext("/index", rootHandler)

            // Handle /messages endpoint
            mHttpServer!!.createContext("/messages", messageHandler)
            mHttpServer!!.createContext("/connection", connectionHandler)

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

    private val rootHandler = HttpHandler { exchange ->
        run {
            when (exchange!!.requestMethod) {
                "GET" -> {
                    sendResponse(exchange, "Message Service - Home Page")
                }
            }
        }
    }

    private val messageHandler = HttpHandler { httpExchange ->
        run {
            when (httpExchange!!.requestMethod) {
                "GET" -> {
                    var id = httpExchange.requestURI.rawQuery
                    sendResponse(httpExchange, "Would be all messages stringified json $id")
                }

                "POST" -> {
                    val inputStream = httpExchange.requestBody

                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)
                    // save message to database

                    //for testing
                    sendResponse(httpExchange, jsonBody.toString())
                }

            }
        }
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

        fun streamToString(inputStream: InputStream): String {
            val s = Scanner(inputStream).useDelimiter("\\A")
            return if (s.hasNext()) s.next() else ""
        }
    }
}