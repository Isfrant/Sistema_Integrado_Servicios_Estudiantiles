package com.example.sistema_integrado_servicios_estudiantiles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuempresa.utsapp.viewmodel.MainViewModel

@Composable
fun Modulo3Screen(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Módulo 3: Histórico Financiero", style = MaterialTheme.typography.titleMedium)

        // Tarjeta destacada con Total Acumulado
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Total Acumulado a Pagar:", style = MaterialTheme.typography.labelMedium)
                Text(
                    text = "$${state.totalAcumulado}",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.liquidaciones) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(item.curso, style = MaterialTheme.typography.titleMedium)
                        Text("Costo base: $${item.costoBase}")
                        Text("Aplica Beca: ${if (item.tieneBeca) "Sí (-50%)" else "No"}")
                        Text("Aplica Icetex: ${if (item.financiaIcetex) "Sí (+5%)" else "No"}")
                        Divider(modifier = Modifier.padding(vertical = 4.dp))
                        Text(
                            text = "Total a Pagar: $${item.totalPagar}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}