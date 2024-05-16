package org.example.dawfilmsinterface.productos.dto

import kotlinx.serialization.Serializable
import org.example.dawfilmsinterface.productos.models.producto.Producto

@Serializable
data class ProductoDto (
    val id: String,
    val tipoProducto: String,
    val filaButaca: Int?,
    val columnaButaca: Int?,
    val tipoButaca: String?,
    val estadoButaca: String?,
    val ocupacionButaca: String?,
    val nombreComplemento: String?,
    val precioComplemento: Double?,
    val stockComplemento: Int?,
    val categoriaComplemento: String?,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean?
)