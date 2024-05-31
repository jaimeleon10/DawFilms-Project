package org.example.dawfilmsinterface.productos.dto

import kotlinx.serialization.Serializable

/**
 * Clase de transferencia de datos (DTO) para representar una butaca.
 * Esta clase se utiliza para transferir datos de butacas entre capas de la aplicación.
 * @property id El identificador único de la butaca.
 * @property tipoProducto El tipo de producto al que pertenece la butaca.
 * @property imagen La URL de la imagen asociada a la butaca.
 * @property fila El número de fila de la butaca en la sala.
 * @property columna El número de columna de la butaca en la sala.
 * @property tipoButaca El tipo de butaca (normal, VIP, etc.).
 * @property estadoButaca El estado actual de la butaca (activa, mantenimiento, etc.).
 * @property ocupacionButaca El estado de ocupación de la butaca (libre, ocupada, en reserva, etc.).
 * @property createdAt La fecha de creación de la butaca.
 * @property updatedAt La fecha de la última actualización de la butaca, nullable.
 * @property isDeleted Indica si la butaca ha sido eliminada, por defecto es falso.
 * @constructor Crea una instancia de ButacaDto con los datos especificados.
 * @author Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
@Serializable
data class ButacaDto(
    val id: String,
    val tipoProducto: String,
    val imagen: String,
    val fila: Int,
    val columna: Int,
    val tipoButaca: String,
    val estadoButaca: String,
    val ocupacionButaca: String,
    val createdAt: String,
    val updatedAt: String? = null,
    val isDeleted: Boolean? = false
)