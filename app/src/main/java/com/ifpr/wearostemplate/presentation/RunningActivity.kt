package com.ifpr.wearostemplate.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.ifpr.wearostemplate.R

class RunningActivity : ComponentActivity() {

    private lateinit var txtTempo: TextView
    private lateinit var btnStop: Button

    private var segundos = 0

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_running)

        txtTempo = findViewById(R.id.txtTempo)
        btnStop = findViewById(R.id.btnStop)

        iniciarCronometro()

        btnStop.setOnClickListener {

            handler.removeCallbacksAndMessages(null)

            finish()

        }

    }

    private fun iniciarCronometro() {

        handler.post(object : Runnable {

            override fun run() {

                segundos++

                val minutos = segundos / 60
                val resto = segundos % 60

                txtTempo.text = String.format("%02d:%02d", minutos, resto)

                handler.postDelayed(this,1000)

            }

        })

    }

}