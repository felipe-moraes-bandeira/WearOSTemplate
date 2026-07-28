package com.ifpr.wearostemplate.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.database.FirebaseDatabase
import com.ifpr.wearostemplate.R
import com.ifpr.wearostemplate.presentation.baseclasses.Corrida
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

            val distanciaKm = 2.5
            val tempoSegundos = segundos.toLong()

            salvarCorrida(distanciaKm, tempoSegundos)

            Toast.makeText(this, "Corrida salva!", Toast.LENGTH_SHORT).show()

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

                handler.postDelayed(this, 1000)
            }

        })

    }

    private fun salvarCorrida(distanciaKm: Double, tempoSegundos: Long) {

        val database = FirebaseDatabase.getInstance()
        val referencia = database.getReference("corridas")

        val id = referencia.push().key ?: return

        val data = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        val ritmo = calcularRitmo(distanciaKm, tempoSegundos)

        val corrida = Corrida(
            distanciaKm,
            tempoSegundos,
            ritmo,
            data
        )

        referencia.child(id).setValue(corrida)
    }

    private fun calcularRitmo(
        distanciaKm: Double,
        tempoSegundos: Long
    ): String {

        if (distanciaKm <= 0.0) return "0:00"

        val segundosPorKm = (tempoSegundos / distanciaKm).toInt()

        val minutos = segundosPorKm / 60
        val segundos = segundosPorKm % 60

        return "$minutos:${segundos.toString().padStart(2, '0')}"
    }
}