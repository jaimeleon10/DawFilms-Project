package org.example.dawfilmsinterface.ventas.dto

import kotlinx.serialization.Serializable
import org.example.dawfilmsinterface.productos.dto.ProductoDto

/**
 * Data class para representar una línea de venta en formato DTO.
 * @param id Identificador único de la línea de venta.
 * @param producto Producto asociado a la línea de venta, representado en formato DTO.
 * @param tipoProducto Tipo del producto asociado a la línea de venta.
 * @param cantidad Cantidad de productos en la línea de venta.
 * @param precio Precio del producto en la línea de venta.
 * @param createdAt Fecha de creación de la línea de venta.
 * @param updatedAt Fecha de última actualización de la línea de venta.
 * @param isDeleted Indicador de si la línea de venta ha sido eliminada.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
@Serializable
data class LineaVentaDto(
    val id: String,
    val producto: ProductoDto,
    val tipoProducto: String,
    val cantidad: Int,
    val precio: Double,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean
)