package com.example.chatclient.pages.chatpage.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.chatclient.R
import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

class CollapsibleConstraintLayout : ConstraintLayout, AppBarLayout.OnOffsetChangedListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleSet: Int) : super(context, attrs, defStyleSet) {}


    init {
        inflate(context, R.layout.chat_page_expanded_toolbar, this)
    }

    private var mTransitionThreshold = 0.35f
    private var mLastPosition: Int = 0
    private var mToolbarOpen = true

    private val mOpenToolBarSet: ConstraintSet = ConstraintSet()
    private val mCloseToolBarSet: ConstraintSet = ConstraintSet()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (parent is AppBarLayout) {
            var appBarLayout = parent as AppBarLayout
            appBarLayout.addOnOffsetChangedListener(this)

            mOpenToolBarSet.clone(context, R.layout.chat_page_expanded_toolbar)
            mCloseToolBarSet.clone(context, R.layout.chat_page_collapsed_toolbar)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
//        if (mLastPosition == verticalOffset) {
//            return
//        }
//        mLastPosition = verticalOffset
//        val progress = abs(verticalOffset / appBarLayout?.height?.toFloat()!!)
//
////        val params = getLayoutParams() as AppBarLayout.LayoutParams
////        params.topMargin = -verticalOffset
////        setLayoutParams(params)
////
//        if (mToolbarOpen && progress > mTransitionThreshold) {
//            mCloseToolBarSet.applyTo(this)
//            mToolbarOpen = false
//            forceLayout()
//        } else if (!mToolbarOpen && progress < mTransitionThreshold) {
//            mOpenToolBarSet.applyTo(this)
//            mToolbarOpen = true
//            forceLayout()
//        }

    }


}