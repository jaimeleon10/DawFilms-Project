package org.example.dawfilmsinterface.ventas.models

import org.example.dawfilmsinterface.productos.models.butacas.Butaca
import org.example.dawfilmsinterface.productos.models.complementos.Complemento
import org.example.dawfilmsinterface.productos.models.producto.Producto
import java.time.LocalDate
import java.util.*

/**
 * Representa una línea de venta que incluye un producto, cantidad, precio, y fechas de creación y actualización.
 * @since 1.0.0
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @property id Identificador único de la línea de venta.
 * @property producto Producto asociado a la línea de venta.
 * @property tipoProducto Tipo de producto ("Butaca" o "Complemento").
 * @property cantidad Cantidad del producto en la línea de venta.
 * @property precio Precio del producto en la línea de venta.
 * @property createdAt Fecha de creación de la línea de venta.
 * @property updatedAt Fecha de última actualización de la línea de venta.
 * @property isDeleted Indica si la línea de venta está eliminada.
 */
data class LineaVenta(
    val id: UUID = UUID.randomUUID(),
    val producto: Producto,
    val tipoProducto: String,
    val cantidad: Int,
    val precio: Double,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val isDeleted: Boolean = false
){

    /**
     * Verifica si el producto es del tipo "Butaca".
     * @return `true` si el producto es una butaca, `false` en caso contrario.
     */
    private fun isButaca(): Boolean{
        return tipoProducto == "Butaca"
    }

    /**
     * Obtiene el identificador del producto. Si el producto es una butaca, retorna "Butaca {id}". Si el producto es un complemento, retorna el nombre del complemento.
     * @return Identificador del producto como una cadena.
     */
    fun obtenerIdProducto(): String{
        return if(isButaca()){
            "Butaca " + (producto as Butaca).id
        } else {
            (producto as Complemento).nombre
        }
    }
}