package org.example.dawfilmsinterface.ventas.models

import org.example.dawfilmsinterface.clientes.models.Cliente
import java.time.LocalDate
import java.util.*

data class Venta(
    val id: UUID = UUID.randomUUID(),
    val cliente: Cliente,
    val lineas: List<LineaVenta>,
    val fechaCompra: LocalDate,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    var isDeleted: Boolean = false
) {
    val total: Double
        get() = lineas.sumOf { it.precio * it.cantidad }
}