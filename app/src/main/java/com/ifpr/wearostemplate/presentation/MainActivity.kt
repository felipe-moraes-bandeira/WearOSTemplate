/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter to find the
 * most up to date changes to the libraries and their usages.
 */

package com.ifpr.wearostemplate.presentation

import kotlinx.coroutines.delay
import androidx.compose.runtime.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.tooling.preview.devices.WearDevices
import com.ifpr.wearostemplate.presentation.theme.WearOSTemplateTheme
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    WearOSTemplateTheme {
        TelaInicial()
    }
}



@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}

@Composable
fun TelaInicial() {
    var tempo by remember { mutableStateOf(0) }
    var rodando by remember { mutableStateOf(false) }

    LaunchedEffect(rodando) {
        while (rodando) {
            delay(1000)
            tempo++
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = String.format("%02d:%02d", tempo / 60, tempo % 60)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    tempo = 0
                    rodando = true
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFF8000)
                )
            ) {
                Text("START")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    rodando = false
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red
                )
            ) {
                Text("STOP")
            }

        }

    }

}