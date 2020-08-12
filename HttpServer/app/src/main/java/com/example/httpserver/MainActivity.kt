package com.example.httpserver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var onOffButton : Button
    private lateinit var debugButton : Button
    private lateinit var serviceTextView: TextView

    private var serverUp = false
    private lateinit var presenter: ServerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = Intent(this, ServerPresenterImpl::class.java)
        startService(intent)
        finish()
    }
}