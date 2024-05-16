package org.example.dawfilmsinterface.clientes.models

import java.time.LocalDate

data class Cliente(
    val id: Long = -1,
    val nombre: String,
    val apellido: String,
    val fechaNacimiento: LocalDate,
    val dni: String,
    val email: String,
    val numSocio: String,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val isDeleted: Boolean = false
)