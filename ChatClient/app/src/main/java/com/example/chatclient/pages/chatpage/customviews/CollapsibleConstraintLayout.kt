package com.example.chatclient.pages.chatpage.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.chatclient.R
import com.google.android.material.appbar.AppBarLayout
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Math.abs

class CollapsibleConstraintLayout : ConstraintLayout, AppBarLayout.OnOffsetChangedListener {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleSet: Int) : super(context, attrs, defStyleSet) {}

    private var mTransitionThreshold = 0.3f
    private var mLastPosition: Int = 0
    private var mToolbarOpen = true

    private var fullNameExpanded: TextView
    private var fullNameCollapsed: TextView

    private var professionExpanded: TextView
    private var professionCollapsed: TextView

    private var backButtonExpanded: ImageView
    private var backButtonCollapsed: ImageView

    private var profilePictureExpanded: CircleImageView
    private var profilePictureCollapsed: CircleImageView

    init {
        inflate(context, R.layout.chat_page_toolbar, this)

        fullNameExpanded = findViewById(R.id.char_page_toolbar_full_name_tw_expanded)
        fullNameCollapsed = findViewById(R.id.char_page_toolbar_full_name_tw_collapsed)

        professionExpanded = findViewById(R.id.chat_page_proffesion_expanded)
        professionCollapsed = findViewById(R.id.chat_page_proffesion_collapsed)

        backButtonExpanded = findViewById(R.id.chat_page_toolbar_back_button_expanded)
        backButtonCollapsed = findViewById(R.id.chat_page_toolbar_back_button_collapsed)

        profilePictureExpanded = findViewById(R.id.chat_page_profile_photo_expanded)
        profilePictureCollapsed = findViewById(R.id.chat_page_profile_photo_collapsed)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (parent is AppBarLayout) {
            var appBarLayout = parent as AppBarLayout
            appBarLayout.addOnOffsetChangedListener(this)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        if (mLastPosition == verticalOffset) {
            return
        }
        mLastPosition = verticalOffset
        val progress = abs(verticalOffset / appBarLayout?.height?.toFloat()!!)

        if (mToolbarOpen && progress > mTransitionThreshold) {
            changeVisibilityToExpandedElements(View.GONE)
            changeVisibilityToCollapsedElements(View.VISIBLE)
            mToolbarOpen = false
        } else if (!mToolbarOpen && progress < mTransitionThreshold) {
            changeVisibilityToCollapsedElements(View.GONE)
            changeVisibilityToExpandedElements(View.VISIBLE)
            mToolbarOpen = true
        }

    }

    private fun changeVisibilityToExpandedElements(newVisibility: Int) {
        fullNameExpanded.visibility = newVisibility
        professionExpanded.visibility = newVisibility
        backButtonExpanded.visibility = newVisibility
        profilePictureExpanded.visibility = newVisibility
    }

    private fun changeVisibilityToCollapsedElements(newVisibility: Int) {
        fullNameCollapsed.visibility = newVisibility
        professionCollapsed.visibility = newVisibility
        backButtonCollapsed.visibility = newVisibility
        profilePictureCollapsed.visibility = newVisibility
    }

}