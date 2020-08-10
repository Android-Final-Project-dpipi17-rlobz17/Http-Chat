package com.example.chatclient.pages.historypage.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

class HistoryToolbarLayout(context: Context, attributes: AttributeSet) : ConstraintLayout(context, attributes) {

    init {
        View.inflate(context, com.example.chatclient.R.layout.history_toolbar_layout, this)
    }

}