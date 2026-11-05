package com.example.sistema_integrado_servicios_estudiantiles.data

class Liquidacion (
    val id: String,
    val curso: String,
    val costoBase: Double,
    val tieneBeca: Boolean,
    val financiaIcetex: Boolean,
    val totalPagar: Double
)