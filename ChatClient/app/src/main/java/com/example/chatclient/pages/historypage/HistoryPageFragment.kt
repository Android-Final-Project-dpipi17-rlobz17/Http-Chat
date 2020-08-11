package com.example.chatclient.pages.historypage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.network.dataclasses.HistoryResponse
import com.example.chatclient.pages.historypage.customViews.HistoryToolbarLayout
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerView
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerViewAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.timerTask


class HistoryPageFragment: Fragment(), HistoryPageContract.View,
    HistoryRecyclerViewAdapter.DataRequestHandler {

    lateinit var mContext : Context
    private lateinit var presenter : HistoryPagePresenterImpl
    private lateinit var toolbar : HistoryToolbarLayout
    private lateinit var searchEditText: EditText
    private lateinit var recyclerView: HistoryRecyclerView
    private lateinit var recyclerViewAdapter: HistoryRecyclerViewAdapter

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
        searchEditText = toolbar.findViewById(R.id.history_toolbar_search_text_view)
        searchEditText.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(
                    s: CharSequence,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                private var timer = Timer()
                private val DELAY: Long = 100
                override fun afterTextChanged(s: Editable) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                if (s.toString().length > 2) {
                                    updateAllDataAndPositionZero()
                                }else{
                                    updateAllDataAndPositionZero()
                                }
                            }
                        },
                        DELAY
                    )
                }
            }
        )

        recyclerView = view.findViewById(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setEmptyView(view.findViewById(R.id.history_recycler_view_empty_text_view))
        ItemTouchHelper(itemTouchHelpCallBack).attachToRecyclerView(recyclerView)
        recyclerViewAdapter = HistoryRecyclerViewAdapter(presenter, findNavController())
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.setUpView(this)

//        Timer().schedule(timerTask {
//            var searchText = searchEditText.text.toString()
//            if(searchText.length <= 2){
//                searchText = ""
//            }
//            GlobalScope.launch {
//                presenter.checkData(searchText)
//            }
//        }, 0, 3000)

        updateAllDataAndPositionZero()
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
                presenter.removeMessages(recyclerViewAdapter.returnAllData()[viewHolder.adapterPosition].friend_nickname)
            }
        }
    }

    // changes for server
    override fun requestNewDataLazyLoading(position: Int) {
        GlobalScope.launch {
            presenter.addNewDataLazyLoading(searchEditText.text.toString(), position)
        }
    }

    private fun updateAllDataAndPositionZero(){
        var searchText = searchEditText.text.toString()
        if(searchText.length <= 2){
            searchText = ""
        }
        GlobalScope.launch {
            presenter.changeData(searchText)
        }
    }


    // changes from server
    override fun newDataForLazyLoading(newData: List<HistoryResponse>) {
        recyclerViewAdapter.addDataForLazyLoading(newData.toMutableList())
    }

    override fun newDataForChange(newData: List<HistoryResponse>) {
        recyclerViewAdapter.changeData(newData.toMutableList())
    }

    override fun getSavedContext(): Context {
        return mContext
    }

    override fun dataNeedsUpdating() {
        updateAllDataAndPositionZero()
    }

    override fun checkIfDataNeedsUpdating(newData: List<HistoryResponse>) {
        val oldData = recyclerViewAdapter.returnAllData()
        if(newData.size > recyclerViewAdapter.returnAllData().size){
            dataNeedsUpdating()
            return
        }

        if(newData.isNotEmpty() and oldData.isNotEmpty()){
            if((newData[0].friend_last_date != oldData[0].friend_last_date) || (newData[0].chat_id != oldData[0].chat_id)){
                dataNeedsUpdating()
                return
            }
        }

    }

}