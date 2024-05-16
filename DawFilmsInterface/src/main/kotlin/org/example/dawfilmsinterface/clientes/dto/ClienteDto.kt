package org.example.dawfilmsinterface.clientes.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ClienteDto(
    val id: Long = -1,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: String,
    val dni: String,
    val email: String,
    val numSocio: String,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean = false
) {
}