package com.example.contae

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.contae.data.Dados
import com.example.contae.navigation.AppNavigation
import com.example.contae.ui.theme.ContaeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dados.inicializar(applicationContext)

        setContent {
            ContaeTheme {
                AppNavigation()
            }
        }
    }
}
