package com.example.chatclient.pages.historypage.recyclerView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HistoryRecyclerView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    private var empty_view : View? = null


    private fun initEmptyView(){
        if(empty_view != null){
            if (adapter == null || adapter!!.itemCount == 0){
                this.visibility = View.GONE
                empty_view!!.visibility  = View.VISIBLE
            }else{
                empty_view!!.visibility = View.GONE
                this.visibility = View.VISIBLE
            }
        }
    }

    private val observer = object : AdapterDataObserver(){
        override fun onChanged() {
            super.onChanged()
            initEmptyView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            initEmptyView()
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = this.adapter
        super.setAdapter(adapter)

        oldAdapter?.unregisterAdapterDataObserver(observer)
        adapter?.registerAdapterDataObserver(observer)
    }

    fun setEmptyView(view: View){
        empty_view = view
        initEmptyView()
    }
}