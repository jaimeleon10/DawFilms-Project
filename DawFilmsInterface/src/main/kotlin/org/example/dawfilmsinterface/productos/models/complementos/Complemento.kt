package org.example.dawfilmsinterface.productos.models.complementos

import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

class Complemento (
    id: String,
    tipoProducto: String = "Complemento",
    imagen : String = "",
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: CategoriaComplemento,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = null,
    isDeleted: Boolean? = false
): Producto(id, tipoProducto, imagen, createdAt, updatedAt, isDeleted) {

    override fun toString(): String {
        return "Complemento(id: $id, nombre: $nombre, tipo: $tipoProducto, imagen: $imagen, precio: $precio, stock: $stock, categoria: $categoria, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        //Comparamos clase padre
        if (!super.equals(other)) return false

        //Tipamos other como Complemento para acceder a sus atributos
        other as Complemento

        //Comparamos atributos
        if (nombre != other.nombre) return false
        if (precio != other.precio) return false
        if (stock != other.stock) return false
        if (categoria != other.categoria) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        //Se utiliza 31 porque es un número primero y es el menos probable de provocar errores
        result = 31 * result + nombre.hashCode()
        result = 31 * result + precio.hashCode()
        result = 31 * result + stock
        result = 31 * result + categoria.hashCode()
        return result
    }
}