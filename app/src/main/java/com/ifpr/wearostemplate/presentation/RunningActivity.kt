package com.ifpr.wearostemplate.presentation

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.ifpr.wearostemplate.R

class RunningActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_running)

        val btnStop = findViewById<Button>(R.id.btnStop)

        btnStop.setOnClickListener {

            finish()

        }

    }

}