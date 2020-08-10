package com.example.httpserver

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ServerContract.View {

    private lateinit var onOffButton : Button
    private lateinit var serviceTextView: TextView

    private var serverUp = false
    private lateinit var presenter: ServerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = ServerPresenterImpl(this, context = this)

        serviceTextView = findViewById(R.id.service_info_text_view)
        onOffButton = findViewById(R.id.service_on_off_button)
        onOffButton.setOnClickListener {
            serverUp = if(!serverUp){
                presenter.startServer()
                true
            } else{
                presenter.stopServer()
                false
            }
        }
    }

    override fun onServerStart() {
        runOnUiThread {
            serviceTextView.text = getString(R.string.service_is_running_text)
            onOffButton.text = getString(R.string.button_stop_service_text)
        }
    }

    override fun onServerStop() {
        runOnUiThread {
            serviceTextView.text = getString(R.string.service_is_running_text)
            onOffButton.text = getString(R.string.button_stop_service_text)
        }
    }


}