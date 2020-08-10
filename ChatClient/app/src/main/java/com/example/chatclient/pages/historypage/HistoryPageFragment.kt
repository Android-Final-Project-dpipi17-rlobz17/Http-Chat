package com.example.chatclient.pages.historypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatclient.R
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerView
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerViewAdapter
import com.example.chatclient.pages.historypage.recyclerView.HistoryRecyclerViewCellModel
import java.util.*

class HistoryPageFragment: Fragment(), HistoryPageContract.View {

    private lateinit var mContext: Context
    private lateinit var recyclerView: HistoryRecyclerView
    private lateinit var recyclerViewAdapter: HistoryRecyclerViewAdapter

    private var data : MutableList<HistoryRecyclerViewCellModel> = listOf(
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze1", "On my way home, but i needed to bring some things, so i stayed at home and I did not have enough time", Date(123422421)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze2", "hello", Date(123422333)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze3", "hello", Date(1234224213)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze4", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", Date(1234224321)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze5", "hello", Date(1234224323)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze6", "hello", Date(1234233331)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze7", "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", Date(123522421)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze8", "hello", Date(1231222421)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze9", "hello", Date(1211422421)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze10", "hello", Date(1299422421)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze11", "hello", Date(1239922421)),
        HistoryRecyclerViewCellModel("", "Rezi Lobzhanidze12", "hello", Date(1234992421))
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

        recyclerView = view.findViewById(R.id.history_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        recyclerView.itemAnimator = DefaultItemAnimator()

        recyclerView.setEmptyView(view.findViewById(R.id.history_recycler_view_empty_text_view))
        ItemTouchHelper(itemTouchHelpCallBack).attachToRecyclerView(recyclerView)
        recyclerViewAdapter = HistoryRecyclerViewAdapter(findNavController())
        recyclerView.adapter = recyclerViewAdapter
        recyclerViewAdapter.updateData(data)

        return view
    }

    override fun onDataUpdate() {
        // TODO[RL] Not yet implemented
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

            data.removeAt(viewHolder.adapterPosition)
            recyclerViewAdapter.updateData(data)
        }
    }

}