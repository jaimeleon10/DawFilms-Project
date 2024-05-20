package org.example.dawfilmsinterface.productos.dto

import kotlinx.serialization.Serializable

@Serializable
data class ComplementoDto(
    val id: String,
    val tipoProducto: String,
    val imagen: String,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: String,
    val createdAt: String,
    val updatedAt: String? = null,
    val isDeleted: Boolean? = false
)