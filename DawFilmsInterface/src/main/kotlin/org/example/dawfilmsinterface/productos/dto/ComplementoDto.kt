package org.example.dawfilmsinterface.productos.dto

import kotlinx.serialization.Serializable

/**
 * Clase de transferencia de datos (DTO) para representar un complemento.
 * Esta clase se utiliza para transferir datos de complementos entre capas de la aplicación.
 * @property id El identificador único del complemento.
 * @property tipoProducto El tipo de producto al que pertenece el complemento.
 * @property imagen La URL de la imagen asociada al complemento.
 * @property nombre El nombre del complemento.
 * @property precio El precio del complemento.
 * @property stock La cantidad disponible en stock del complemento.
 * @property categoria La categoría del complemento (bebida, comida, etc.).
 * @property createdAt La fecha de creación del complemento.
 * @property updatedAt La fecha de la última actualización del complemento, nullable.
 * @property isDeleted Indica si el complemento ha sido eliminado, por defecto es falso.
 * @constructor Crea una instancia de ComplementoDto con los datos especificados.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
@Serializable
data class ComplementoDto(
    val id: String,
    val tipoProducto: String,
    val imagen: String,
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: String,
    val createdAt: String,
    val updatedAt: String? = null,
    val isDeleted: Boolean? = false
)