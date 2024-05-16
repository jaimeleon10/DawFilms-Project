package org.example.dawfilmsinterface.ventas.dto

import kotlinx.serialization.Serializable
import org.example.dawfilmsinterface.clientes.dto.ClienteDto

@Serializable
data class VentaDto(
    val id: String,
    val cliente: ClienteDto,
    val lineas: List<LineaVentaDto>,
    val total: Double,
    val fechaCompra: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)