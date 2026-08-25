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

class RunningActivity : ComponentActivity() {

    private lateinit var txtTempo: TextView
    private lateinit var btnStop: Button

    private var segundos = 0

    private val handler =
        Handler(Looper.getMainLooper())

    private val cronometro =
        object : Runnable {

            override fun run() {

                segundos++

                val minutos =
                    segundos / 60

                val resto =
                    segundos % 60

                txtTempo.text =
                    String.format(
                        "%02d:%02d",
                        minutos,
                        resto
                    )

                handler.postDelayed(
                    this,
                    1000L
                )
            }
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_running
        )

        txtTempo =
            findViewById(R.id.txtTempo)

        btnStop =
            findViewById(R.id.btnStop)

        iniciarCronometro()

        btnStop.setOnClickListener {

            pararCronometro()

            val distanciaKm =
                2.5

            val tempoSegundos =
                segundos.toLong()

            salvarCorrida(
                distanciaKm,
                tempoSegundos
            )
        }
    }

    // =========================================================
    // CRONÔMETRO
    // =========================================================

    private fun iniciarCronometro() {

        segundos = 0

        txtTempo.text = "00:00"

        handler.postDelayed(
            cronometro,
            1000L
        )
    }

    private fun pararCronometro() {

        handler.removeCallbacks(
            cronometro
        )
    }

    // =========================================================
    // SALVAR CORRIDA
    // =========================================================

    private fun salvarCorrida(
        distanciaKm: Double,
        tempoSegundos: Long
    ) {

        val database =
            FirebaseDatabase.getInstance()

        val referencia =
            database.getReference("corridas")

        val id =
            referencia
                .push()
                .key
                ?: return

        // Ritmo em minutos por km.
        val ritmo =
            calcularRitmo(
                distanciaKm,
                tempoSegundos
            )

        // Velocidade média em km/h.
        val velocidadeMedia =
            calcularVelocidadeMedia(
                distanciaKm,
                tempoSegundos
            )

        // Peso temporário.
        // Posteriormente pode vir do Perfil.
        val pesoKg = 70.0

        val calorias =
            calcularCalorias(
                distanciaKm,
                pesoKg
            )

        val corrida =
            Corrida(
                id = id,
                distanciaKm = distanciaKm,
                tempoSegundos = tempoSegundos,
                ritmoMedio = ritmo,
                velocidadeMedia = velocidadeMedia,
                calorias = calorias,
                data = System.currentTimeMillis()
            )

        referencia
            .child(id)
            .setValue(corrida)
            .addOnSuccessListener {

                Toast.makeText(
                    this,
                    "Corrida salva!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
            .addOnFailureListener {

                Toast.makeText(
                    this,
                    "Erro ao salvar corrida.",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    // =========================================================
    // CALCULAR RITMO
    // =========================================================

    private fun calcularRitmo(
        distanciaKm: Double,
        tempoSegundos: Long
    ): Double {

        if (distanciaKm <= 0.0) {
            return 0.0
        }

        if (tempoSegundos <= 0L) {
            return 0.0
        }

        /*
         * Retorna minutos por quilômetro.
         *
         * Exemplo:
         * 30 minutos / 5 km = 6.0 min/km
         */
        val tempoMinutos =
            tempoSegundos / 60.0

        return tempoMinutos /
                distanciaKm
    }

    // =========================================================
    // VELOCIDADE MÉDIA
    // =========================================================

    private fun calcularVelocidadeMedia(
        distanciaKm: Double,
        tempoSegundos: Long
    ): Double {

        if (distanciaKm <= 0.0) {
            return 0.0
        }

        if (tempoSegundos <= 0L) {
            return 0.0
        }

        val tempoHoras =
            tempoSegundos / 3600.0

        return distanciaKm /
                tempoHoras
    }

    // =========================================================
    // CALORIAS
    // =========================================================

    private fun calcularCalorias(
        distanciaKm: Double,
        pesoKg: Double
    ): Double {

        return distanciaKm *
                pesoKg *
                1.036
    }

    // =========================================================
    // ON DESTROY
    // =========================================================

    override fun onDestroy() {

        pararCronometro()

        super.onDestroy()
    }
}
