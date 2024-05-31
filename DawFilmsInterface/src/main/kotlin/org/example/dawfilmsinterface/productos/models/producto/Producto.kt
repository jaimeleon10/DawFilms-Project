package org.example.dawfilmsinterface.productos.models.producto

import java.time.LocalDate

/**
 * Clase abstracta que representa un producto en el sistema.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property id Identificador único del producto.
 * @property tipoProducto Tipo de producto.
 * @property imagen URL de la imagen del producto.
 * @property createdAt Fecha de creación del producto.
 * @property updatedAt Fecha de la última actualización del producto.
 * @property isDeleted Indica si el producto ha sido eliminado.
 */
abstract class Producto(
    var id: String,
    var tipoProducto: String,
    val imagen : String,
    val createdAt: LocalDate,
    val updatedAt: LocalDate?,
    val isDeleted: Boolean?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        //Tipamos other como Producto para acceder a sus atributos
        other as Producto

        if (id != other.id) return false
        if (tipoProducto != other.tipoProducto) return false
        if (imagen != other.imagen) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (isDeleted != other.isDeleted) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        //Se utiliza 31 porque es un número primero y es el menos probable de provocar errores
        result = 31 * result + tipoProducto.hashCode()
        result = 31 * result + imagen.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + (updatedAt?.hashCode() ?: 0)
        result = 31 * result + (isDeleted?.hashCode() ?: 0)
        return result
    }
}