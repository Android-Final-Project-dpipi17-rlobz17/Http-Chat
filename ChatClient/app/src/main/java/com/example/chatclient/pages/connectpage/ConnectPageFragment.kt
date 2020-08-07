package com.example.chatclient.pages.connectpage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.chatclient.R
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConnectPageFragment : Fragment(), ConnectPageContract.View {

    private lateinit var mContext: Context
    private lateinit var presenter: ConnectPageContract.Presenter
    private lateinit var progressBar: AVLoadingIndicatorView
    private lateinit var retryButton: Button
    private lateinit var connectionInfoTextView: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.connect_fragment_layout, container, false)
        initFields(view)
        checkConnection()
        return view
    }

    private fun initFields(view: View) {
        activity?.applicationContext?.let {
            presenter = ConnectPagePresenterImpl(this, it)
        }
        progressBar = view.findViewById(R.id.connect_fragment_layout_progress_bar)
        connectionInfoTextView = view.findViewById(R.id.connect_fragment_layout_info_text_view)
        retryButton = view.findViewById(R.id.connect_fragment_layout_retry_button)

        retryButton.setOnClickListener {
            checkConnection()
        }
    }

    private fun checkConnection() {
        retryButton.visibility = View.INVISIBLE
        progressBar.smoothToShow()
        connectionInfoTextView.text = getString(R.string.check_connection)
        GlobalScope.launch {
            presenter.checkConnection()
        }
    }

    override fun onConnectionSuccess() {
        (mContext as Activity).runOnUiThread {
            progressBar.smoothToHide()

            // TODO[DP] navigate
            // findNavController().navigate(R.id.action_connectPageFragment_to_MessagesPageFragment)
        }
    }

    override fun onConnectionFailure() {
        (mContext as Activity).runOnUiThread {
            progressBar.hide()
            connectionInfoTextView.text = getString(R.string.connection_failure)
            retryButton.visibility = View.VISIBLE
        }
    }
}