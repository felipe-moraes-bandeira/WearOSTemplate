package com.ifpr.wearostemplate.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ifpr.wearostemplate.R

class MainActivity : ComponentActivity() {

    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener {

            val intent = Intent(this, RunningActivity::class.java)

            startActivity(intent)

        }

    }

}