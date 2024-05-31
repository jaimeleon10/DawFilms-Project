package org.example.dawfilmsinterface.clientes.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

/**
 * Clase de transferencia de datos para representar un cliente.
 * @param id Identificador del cliente. Por defecto, es -1.
 * @param nombre Nombre del cliente.
 * @param apellido Apellido del cliente.
 * @param fechaNacimiento Fecha de nacimiento del cliente en formato de cadena.
 * @param dni DNI del cliente.
 * @param email Correo electrónico del cliente.
 * @param numSocio Número de socio del cliente.
 * @param password Contraseña del cliente.
 * @param createdAt Fecha y hora de creación del registro del cliente en formato de cadena.
 * @param updatedAt Fecha y hora de la última actualización del registro del cliente en formato de cadena.
 * @param isDeleted Indica si el cliente ha sido marcado como eliminado. Por defecto, es false.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
@Serializable
data class ClienteDto(
    val id: Long = -1,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val dni: String,
    val email: String,
    val numSocio: String,
    val password: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean = false
)