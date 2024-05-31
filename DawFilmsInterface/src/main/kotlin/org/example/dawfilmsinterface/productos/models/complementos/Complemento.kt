package org.example.dawfilmsinterface.productos.models.complementos

import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.butacas.EstadoButaca
import org.example.dawfilmsinterface.productos.models.butacas.OcupacionButaca
import org.example.dawfilmsinterface.productos.models.butacas.TipoButaca
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate

/**
 * Clase que representa un complemento en el sistema.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 * @property id Identificador único del complemento.
 * @property tipoProducto Tipo de producto al que pertenece el complemento. Por defecto es "Complemento".
 * @property imagen URL de la imagen del complemento. Por defecto es una cadena vacía.
 * @property nombre Nombre del complemento.
 * @property precio Precio del complemento.
 * @property stock Cantidad disponible en stock del complemento.
 * @property categoria Categoría del complemento.
 * @property createdAt Fecha de creación del complemento. Por defecto es la fecha y hora actual.
 * @property updatedAt Fecha de la última actualización del complemento. Por defecto es la fecha y hora actual.
 * @property isDeleted Indica si el complemento ha sido eliminado. Por defecto es falso.
 */
class Complemento (
    id: String,
    tipoProducto: String = "Complemento",
    imagen : String = "",
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: CategoriaComplemento,
    createdAt: LocalDate = LocalDate.now(),
    updatedAt: LocalDate? = LocalDate.now(),
    isDeleted: Boolean? = false
): Producto(id, tipoProducto, imagen, createdAt, updatedAt, isDeleted) {

    override fun toString(): String {
        return "Complemento(id: $id, nombre: $nombre, tipo: $tipoProducto, imagen: $imagen, precio: $precio, stock: $stock, categoria: $categoria, createdAt: $createdAt, updatedAt: $updatedAt, isDeleted: $isDeleted)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

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

    fun copy(
        id: String = this.id,
        tipoProducto: String = this.tipoProducto,
        imagen: String = this.imagen,
        nombre: String = this.nombre,
        precio: Double = this.precio,
        stock: Int = this.stock,
        categoria: CategoriaComplemento = this.categoria,
        createdAt: LocalDate = this.createdAt,
        updatedAt: LocalDate? = this.updatedAt,
        isDeleted: Boolean? = this.isDeleted
    ) : Complemento {
        return Complemento(id, tipoProducto, imagen, nombre, precio, stock, categoria, createdAt, updatedAt, isDeleted)
    }
}