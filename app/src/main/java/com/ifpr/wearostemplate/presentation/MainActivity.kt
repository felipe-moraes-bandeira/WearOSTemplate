package com.ifpr.wearostemplate.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ifpr.wearostemplate.R

class MainActivity : ComponentActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnPerfil: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContentView(R.layout.activity_main)

        // Liga os botões ao XML
        btnStart = findViewById(R.id.btnStart)
        btnPerfil = findViewById(R.id.btnPerfil)

        // Botão START
        btnStart.setOnClickListener {

            val intent = Intent(this, RunningActivity::class.java)
            startActivity(intent)

        }

        // Botão PERFIL
        btnPerfil.setOnClickListener {

            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)

        }

    }
}