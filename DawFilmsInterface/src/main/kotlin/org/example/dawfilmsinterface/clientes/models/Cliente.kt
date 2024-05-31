package org.example.dawfilmsinterface.clientes.models

import java.time.LocalDate

/**
 * Clase de datos que representa un cliente.
 * @param id Identificador del cliente. Por defecto, es -1.
 * @param nombre Nombre del cliente.
 * @param apellido Apellido del cliente.
 * @param fechaNacimiento Fecha de nacimiento del cliente.
 * @param dni DNI del cliente.
 * @param email Correo electrónico del cliente.
 * @param numSocio Número de socio del cliente.
 * @param password Contraseña del cliente.
 * @param createdAt Fecha de creación del cliente. Por defecto, es la fecha actual.
 * @param updatedAt Fecha de última actualización del cliente. Por defecto, es la fecha actual.
 * @param isDeleted Indica si el cliente ha sido marcado como eliminado. Por defecto, es false.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
data class Cliente(
    val id: Long = -1,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: LocalDate,
    val dni: String,
    val email: String,
    val numSocio: String,
    val password: String,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val isDeleted: Boolean = false
)