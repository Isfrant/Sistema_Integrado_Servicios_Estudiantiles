package com.example.sistema_integrado_servicios_estudiantiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sistema_integrado_servicios_estudiantiles.ui.theme.Sistema_Integrado_Servicios_EstudiantilesTheme

class MainActivity : ComponentActivity() {

    // Instanciación del ViewModel usando el delegado de KTX (sobrevive a cambios de configuración como rotaciones)
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UtsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamas al contenedor principal de navegación pasando el ViewModel
                    MainScreen(viewModel = mainViewModel)
                }
            }
        }
    }
}