package org.example.dawfilmsinterface.clientes.mappers

import database.UsuarioEntity
import org.example.dawfilmsinterface.cine.viewModels.LoginViewModel.ClienteState
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
        password = this.password,
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
        password = this.password,
        createdAt = this.createdAt.toString(),
        updatedAt = this.updatedAt.toString(),
        isDeleted = this.isDeleted
    )
}

fun ClienteDto.toCliente(): Cliente{
    return Cliente(
        id = this.id,
        nombre = this.nombre,
        apellido = this.apellido,
        fechaNacimiento = LocalDate.parse(this.fechaNacimiento),
        dni = this.dni,
        email = this.email,
        numSocio = this.numSocio,
        password = this.password,
        createdAt = LocalDate.parse(this.createdAt),
        updatedAt = LocalDate.parse(this.updatedAt),
        isDeleted = this.isDeleted
    )
}

fun List<Cliente>.toClienteDtoList(): List<ClienteDto>{
    return map { it.toClienteDto() }
}

fun List<ClienteDto>.toClienteList(): List<Cliente>{
    return map{ it.toCliente() }
}