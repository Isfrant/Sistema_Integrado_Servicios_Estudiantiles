package com.example.sistema_integrado_servicios_estudiantiles.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuempresa.utsapp.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Modulo2Screen(viewModel: MainViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Desafío del semestre: deshabilitar si no ha completado el módulo 1
    val isPerfilValido = state.estudiante.correoInstitucional.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Módulo 2: Liquidación Extracurricular", style = MaterialTheme.typography.titleMedium)

        if (!isPerfilValido) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Debe completar el Perfil en el Módulo 1 para habilitar la liquidación.",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        ExposedDropdownMenuBox(
            expanded = state.isCursoExpanded,
            onExpandedChange = { if (isPerfilValido) viewModel.onCursoExpandedChange(it) },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = state.cursoSeleccionado?.let { "${it.nombre} ($${it.costoBase})" } ?: "",
                onValueChange = {},
                readOnly = true,
                enabled = isPerfilValido,
                label = { Text("Seleccionar Curso Libre") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isCursoExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = state.isCursoExpanded,
                onDismissRequest = { viewModel.onCursoExpandedChange(false) }
            ) {
                state.cursosDisponibles.forEach { curso ->
                    DropdownMenuItem(
                        text = { Text("${curso.nombre} - $${curso.costoBase}") },
                        onClick = { viewModel.onCursoSelected(curso) }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = state.tieneBeca,
                onCheckedChange = { viewModel.onBecaChanged(it) },
                enabled = isPerfilValido
            )
            Text("Beca de Excelencia UTS (50% Descuento)")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Switch(
                checked = state.financiaIcetex,
                onCheckedChange = { viewModel.onIcetexChanged(it) },
                enabled = isPerfilValido
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Financiación ICETEX (+5% Recargo)")
        }

        Button(
            onClick = { viewModel.liquidarMatricula() },
            enabled = isPerfilValido && state.cursoSeleccionado != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Liquidar")
        }
    }
}