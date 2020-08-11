package com.example.chatclient.pages.chatpage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.SharedPreferencesInfo
import com.example.chatclient.pages.chatpage.customviews.ChatPageMessageInput
import com.example.chatclient.pages.chatpage.customviews.CollapsibleConstraintLayout
import com.example.chatclient.pages.chatpage.customviews.CollapsibleToolBarLayoutModel
import com.example.chatclient.pages.chatpage.recyclerview.ChatPageRecyclerViewAdapter
import com.example.chatclient.pages.chatpage.recyclerview.MessageCellModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ChatPageFragment : Fragment(), ChatPageContract.View,
    CollapsibleConstraintLayout.BackButtonClickHandler, ChatPageMessageInput.SendButtonClickHandler {

    private lateinit var mContext: Context
    private lateinit var presenter: ChatPageContract.Presenter
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: ChatPageRecyclerViewAdapter
    private lateinit var toolBar: CollapsibleConstraintLayout
    private lateinit var messageInput: ChatPageMessageInput

    private lateinit var myNickName: String
    private lateinit var friendNickName: String
    private var chatId: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment_layout, container, false)

        initFields(view)

        val sharedPreferences = view.getContext().getSharedPreferences(SharedPreferencesInfo.MY_PREFERENCES, Context.MODE_PRIVATE)
        myNickName = sharedPreferences.getString(SharedPreferencesInfo.MY_PREFERENCES_NICKNAME_KEY, "")!!
        friendNickName = arguments?.getString("friendNickName").toString()
        chatId = arguments?.getInt("chatId")!!

        GlobalScope.launch {
            presenter.fetchMessages(myNickName, friendNickName)

            (mContext as Activity).runOnUiThread {
                recyclerView.scrollToPosition(0)
            }
        }

        view.findViewById<AppBarLayout>(R.id.chatPageAppBarLayout).setExpanded(false)
        return view
    }

    private fun initFields(view: View) {
        activity?.applicationContext?.let {
            presenter = ChatPagePresenterImpl(this, it)
        }
        toolBar = view.findViewById(R.id.chatPageToolBar)

        recyclerView = view.findViewById(R.id.chatPageRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        adapter = ChatPageRecyclerViewAdapter()
        recyclerView.adapter = adapter

        messageInput = view.findViewById(R.id.chatPageMessageInput)
        messageInput.setUpView(this)
    }

    override fun onClick() {
        findNavController().popBackStack()
    }

    override fun onEditTextClick() {
        recyclerView.smoothScrollToPosition(adapter.itemCount)
    }

    override fun onSendButtonClickHandler(message: String) {
        if (message.isNotEmpty()) {
            GlobalScope.launch {
                presenter.sendMessage(myNickName, friendNickName, message, chatId)

                (mContext as Activity).runOnUiThread {
                    adapter.addMessage(MessageCellModel(message, true, Date()))
                    recyclerView.scrollToPosition(0)
                }
            }
        }

    }

    override fun setUpToolBar(collapsibleToolBarLayoutModel: CollapsibleToolBarLayoutModel) {
        toolBar.setUpView(collapsibleToolBarLayoutModel, this)
    }

    override fun updateRecyclerView(cells: List<MessageCellModel>) {
        adapter.setUpCells(ArrayList(cells))
    }
}