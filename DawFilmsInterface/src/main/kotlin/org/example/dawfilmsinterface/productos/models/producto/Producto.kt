package org.example.dawfilmsinterface.productos.models.producto

import java.time.LocalDate

abstract class Producto(
    val id: String,
    var tipoProducto: String,
    val imagen : String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate?,
    val isDeleted: Boolean?
)