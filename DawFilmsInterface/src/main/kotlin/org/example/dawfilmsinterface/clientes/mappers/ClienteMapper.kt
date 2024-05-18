package org.example.dawfilmsinterface.clientes.mappers

import database.UsuarioEntity
import org.example.dawfilmsinterface.clientes.dto.ClienteDto
import org.example.dawfilmsinterface.clientes.models.Cliente
import java.time.LocalDate

fun UsuarioEntity.toCliente(): Cliente {
    return Cliente(
        id = this.id,
        nombre = this.nombre,
        apellido = this.apellido,
        fechaNacimiento = LocalDate.parse(this.fecha_nacimiento),
        dni = this.dni,
        email = this.email,
        numSocio = this.numSocio,
        createdAt = LocalDate.parse(this.created_at),
        updatedAt = LocalDate.parse(this.updated_at),
        isDeleted = this.is_deleted.toInt() == 1
    )
}

fun Cliente.toClienteDto(): ClienteDto {
    return ClienteDto(
        id = this.id,
        nombre = this.nombre,
        apellido = this.apellido,
        fechaNacimiento = this.fechaNacimiento.toString(),
        dni = this.dni,
        email = this.email,
        numSocio = this.numSocio,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}