package org.example.dawfilmsinterface.ventas.models

import org.example.dawfilmsinterface.clientes.models.Cliente
import java.time.LocalDate
import java.util.*

/**
 * Representa una venta que incluye un cliente, líneas de venta, fecha de compra y fechas de creación y actualización.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @property id Identificador único de la venta.
 * @property cliente Cliente asociado a la venta.
 * @property lineas Lista de líneas de venta asociadas a la venta.
 * @property fechaCompra Fecha de la compra.
 * @property createdAt Fecha de creación de la venta.
 * @property updatedAt Fecha de última actualización de la venta.
 * @property isDeleted Indica si la venta está eliminada.
 */
data class Venta(
    val id: UUID = UUID.randomUUID(),
    val cliente: Cliente,
    val lineas: List<LineaVenta>,
    val fechaCompra: LocalDate,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    var isDeleted: Boolean = false
) {
    /**
     * Calcula el total de la venta sumando el precio multiplicado por la cantidad de cada línea de venta.
     */
    val total: Double
        get() = lineas.sumOf { it.precio * it.cantidad }
}