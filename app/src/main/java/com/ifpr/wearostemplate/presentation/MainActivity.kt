/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.ifpr.wearostemplate.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ifpr.wearostemplate.R
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.content.Intent
class MainActivity : ComponentActivity() {
    private lateinit var txtTempo: TextView
    private lateinit var btnStart: Button

    private lateinit var btnStop: Button
    private var segundos = 0
    private var rodando = false

    private val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContentView(R.layout.activity_main)


        txtTempo = findViewById(R.id.txtTempo)
        btnStart = findViewById(R.id.btnStart)
        btnStop = findViewById(R.id.btnStop)

        btnStart.setOnClickListener {

            val intent = Intent(this, RunningActivity::class.java)

            startActivity(intent)

        }
        btnStop.setOnClickListener {

            // Pausa o cronômetro
            rodando = false

        }
        iniciarCronometro()
    }

    private fun iniciarCronometro() {

        handler.post(object : Runnable {

            override fun run() {

                if (rodando) {

                    segundos++

                    val minutos = segundos / 60
                    val resto = segundos % 60

                    txtTempo.text = String.format("%02d:%02d", minutos, resto)

                }

                handler.postDelayed(this,1000)

            }

        })

    }

}

