package org.example.dawfilmsinterface.ventas.dto

import kotlinx.serialization.Serializable
import org.example.dawfilmsinterface.productos.dto.ProductoDto

@Serializable
data class LineaVentaDto(
    val id: String,
    val producto: ProductoDto,
    val tipoProducto: String,
    val cantidad: Int,
    val precio: Double,
    val createdAt: String,
    val updatedAt: String
)