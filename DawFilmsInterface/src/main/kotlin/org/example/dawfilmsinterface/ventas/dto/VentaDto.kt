package org.example.dawfilmsinterface.ventas.dto

import kotlinx.serialization.Serializable
import org.example.dawfilmsinterface.clientes.dto.ClienteDto


/**
 * Data class para representar una venta en formato DTO.
 * @param id Identificador único de la venta.
 * @param cliente Cliente asociado a la venta, representado en formato DTO.
 * @param lineas Lista de líneas de venta asociadas a la venta.
 * @param total Total de la venta.
 * @param fechaCompra Fecha de la compra.
 * @param createdAt Fecha de creación de la venta.
 * @param updatedAt Fecha de última actualización de la venta.
 * @param isDeleted Indicador de si la venta ha sido eliminada.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 */
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