package org.example.dawfilmsinterface.productos.dto

import kotlinx.serialization.Serializable
import org.example.dawfilmsinterface.productos.models.producto.Producto

/**
 * Clase de transferencia de datos (DTO) para representar un producto genérico.
 * Esta clase se utiliza para transferir datos de productos entre capas de la aplicación.
 * @property id El identificador único del producto.
 * @property tipoProducto El tipo de producto.
 * @property imagen La URL de la imagen asociada al producto.
 * @property filaButaca La fila de la butaca asociada al producto, nullable.
 * @property columnaButaca La columna de la butaca asociada al producto, nullable.
 * @property tipoButaca El tipo de la butaca asociada al producto, nullable.
 * @property estadoButaca El estado de la butaca asociada al producto, nullable.
 * @property ocupacionButaca La ocupación de la butaca asociada al producto, nullable.
 * @property nombreComplemento El nombre del complemento asociado al producto, nullable.
 * @property precioComplemento El precio del complemento asociado al producto, nullable.
 * @property stockComplemento El stock disponible del complemento asociado al producto, nullable.
 * @property categoriaComplemento La categoría del complemento asociado al producto, nullable.
 * @property createdAt La fecha de creación del producto.
 * @property updatedAt La fecha de la última actualización del producto.
 * @property isDeleted Indica si el producto ha sido eliminado, nullable.
 * @constructor Crea una instancia de ProductoDto con los datos especificados.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
@Serializable
data class ProductoDto (
    val id: String,
    val tipoProducto: String,
    val imagen : String,
    val filaButaca: Int?,
    val columnaButaca: Int?,
    val tipoButaca: String?,
    val estadoButaca: String?,
    val ocupacionButaca: String?,
    val nombreComplemento: String?,
    val precioComplemento: Double?,
    val stockComplemento: Int?,
    val categoriaComplemento: String?,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean?
)