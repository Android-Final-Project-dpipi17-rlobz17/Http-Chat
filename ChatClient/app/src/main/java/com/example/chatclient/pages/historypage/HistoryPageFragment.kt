package com.example.chatclient.pages.historypage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.network.api.dataClasses.HistoryItem
import com.example.chatclient.pages.historypage.customViews.HistoryToolbarLayout
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerView
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerViewAdapter
import com.example.chatclient.pages.loginpage.LoginPagePresenterImpl
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import java.util.*

class HistoryPageFragment: Fragment(), HistoryPageContract.View {

    private lateinit var mContext : Context
    private lateinit var presenter : HistoryPagePresenterImpl
    private lateinit var toolbar : HistoryToolbarLayout
    private lateinit var recyclerView: HistoryRecyclerView
    private lateinit var recyclerViewAdapter: HistoryRecyclerViewAdapter

    private var data : MutableList<HistoryItem> = listOf(
        HistoryItem("", "Rezi Lobzhanidze1", "On my way home, but i needed to bring some things, so i stayed at home and I did not have enough time", Date(123422421)),
        HistoryItem("", "Rezi Lobzhanidze2", "hello", Date(123422333)),
        HistoryItem("", "Rezi Lobzhanidze3", "hello", Date(1234224213)),
        HistoryItem("", "Rezi Lobzhanidze4", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Date(1234224321)),
        HistoryItem("", "Rezi Lobzhanidze5", "hello", Date(1234224323)),
        HistoryItem("", "Rezi Lobzhanidze6", "hello", Date(1234233331)),
        HistoryItem("", "Rezi Lobzhanidze7", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", Date(123522421)),
        HistoryItem("", "Rezi Lobzhanidze8", "hello", Date(1231222421)),
        HistoryItem("", "Rezi Lobzhanidze9", "hello", Date(1211422421)),
        HistoryItem("", "Rezi Lobzhanidze10", "hello", Date(1299422421)),
        HistoryItem("", "Rezi Lobzhanidze11", "hello", Date(1239922421)),
        HistoryItem("", "Rezi Lobzhanidze12", "hello", Date(1234992421))
    ).toMutableList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.history_fragment_layout, container, false)

        activity?.applicationContext?.let {
            presenter = HistoryPagePresenterImpl(this, it)
        }

        toolbar = view.findViewById(R.id.history_toolbar_layout)
        toolbar.findViewById<TextView>(R.id.history_toolbar_search_text_view).addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s != null){
                    if(s.length > 3){
                        presenter.getData(s.toString(), 0)
                    }
                }
            }
        })

        recyclerView = view.findViewById(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setEmptyView(view.findViewById(R.id.history_recycler_view_empty_text_view))
        ItemTouchHelper(itemTouchHelpCallBack).attachToRecyclerView(recyclerView)
        recyclerViewAdapter = HistoryRecyclerViewAdapter(presenter, findNavController())
        recyclerView.adapter = recyclerViewAdapter

        // recyclerViewAdapter.updateData(data, "")

        GlobalScope.launch {
            presenter.getData("", 0)
        }

        return view
    }

    private val itemTouchHelpCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            GlobalScope.launch {
                presenter.removeMessages(data[viewHolder.adapterPosition].nickname)
            }
        }
    }

    override fun updateData(newData: List<HistoryItem>, searchString: String) {
        data = newData.toMutableList()
        (mContext as Activity).runOnUiThread {
            recyclerViewAdapter.updateData(data, searchString)
        }
    }
}