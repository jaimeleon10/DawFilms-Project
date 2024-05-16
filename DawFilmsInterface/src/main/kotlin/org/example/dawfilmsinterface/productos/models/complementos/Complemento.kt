package org.example.dawfilmsinterface.productos.models.complementos

import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

class Complemento (
    id: String,
    tipoProducto: String = "Complemento",
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: CategoriaComplemento,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = null,
    isDeleted: Boolean? = false
): Producto(id, tipoProducto, createdAt, updatedAt, isDeleted) {

    override fun toString(): String {
        return "Complemento(id: $id, nombre: $nombre, tipo: $tipoProducto, precio: $precio, stock: $stock, categoria: $categoria, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }
}