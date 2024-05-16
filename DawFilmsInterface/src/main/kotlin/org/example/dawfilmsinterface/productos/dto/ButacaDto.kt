package org.example.dawfilmsinterface.productos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ButacaDto(
    val id: String,
    val tipoProducto: String,
    val fila: Int,
    val columna: Int,
    val tipoButaca: String,
    val estadoButaca: String,
    val ocupacionButaca: String,
    val createdAt: String,
    val updatedAt: String? = null,
    val isDeleted: Boolean? = false
)