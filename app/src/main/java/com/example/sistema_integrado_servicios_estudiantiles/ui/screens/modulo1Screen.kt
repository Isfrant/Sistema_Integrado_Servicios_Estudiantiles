package com.example.sistema_integrado_servicios_estudiantiles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuempresa.utsapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Modulo1Screen(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Módulo 1: Registro y Perfil Académico", style = MaterialTheme.typography.titleMedium)

        OutlinedTextField(
            value = state.estudiante.nombre,
            onValueChange = { viewModel.onNombreChange(it) },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.estudiante.codigo,
            onValueChange = { viewModel.onCodigoChange(it) },
            label = { Text("Código Estudiantil") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = state.isFacultadExpanded,
            onExpandedChange = { viewModel.onFacultadExpandedChange(it) },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.estudiante.facultad,
                onValueChange = {},
                readOnly = true,
                label = { Text("Facultad") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isFacultadExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = state.isFacultadExpanded,
                onDismissRequest = { viewModel.onFacultadExpandedChange(false) }
            ) {
                state.facultades.forEach { facultad ->
                    DropdownMenuItem(
                        text = { Text(facultad) },
                        onClick = { viewModel.onFacultadSelected(facultad) }
                    )
                }
            }
        }

        Button(
            onClick = { viewModel.guardarPerfil() },
            enabled = state.estudiante.nombre.isNotBlank() &&
                    state.estudiante.codigo.isNotBlank() &&
                    state.estudiante.facultad.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Perfil")
        }

        if (state.estudiante.correoInstitucional.isNotEmpty()) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Correo Generado:", style = MaterialTheme.typography.labelLarge)
                    Text(state.estudiante.correoInstitucional, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}