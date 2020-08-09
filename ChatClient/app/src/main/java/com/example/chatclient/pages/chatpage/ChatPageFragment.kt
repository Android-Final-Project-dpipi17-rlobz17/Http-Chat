package com.example.chatclient.pages.chatpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.pages.chatpage.recyclerview.ChatPageRecyclerViewAdapter

class ChatPageFragment : Fragment(), ChatPageContract.View {

    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment_layout, container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.chatPageRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this.context)
        val adapter = ChatPageRecyclerViewAdapter()
        recyclerView?.adapter = adapter

        return view
    }
}