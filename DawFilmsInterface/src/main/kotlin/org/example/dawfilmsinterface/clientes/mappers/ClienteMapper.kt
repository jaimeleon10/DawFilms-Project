package org.example.dawfilmsinterface.clientes.mappers

import database.UsuarioEntity
import org.example.dawfilmsinterface.clientes.dto.ClienteDto
import org.example.dawfilmsinterface.clientes.models.Cliente
import java.time.LocalDate

/**
 * Convierte esta entidad de usuario en un objeto de tipo [Cliente].
 * @return Un objeto de tipo [Cliente] creado a partir de esta entidad de usuario.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
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

/**
 * Convierte este objeto de tipo [Cliente] en un objeto de tipo [ClienteDto].
 * @return Un objeto de tipo [ClienteDto] creado a partir de este cliente.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
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

/**
 * Convierte este objeto de tipo [ClienteDto] en un objeto de tipo [Cliente].
 * @return Un objeto de tipo [Cliente] creado a partir de este cliente DTO.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
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

/**
 * Convierte una lista de objetos de tipo [Cliente] en una lista de objetos de tipo [ClienteDto].
 * @return Una lista de objetos de tipo [ClienteDto] creada a partir de la lista de clientes.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
fun List<Cliente>.toClienteDtoList(): List<ClienteDto>{
    return map { it.toClienteDto() }
}

/**
 * Convierte una lista de objetos de tipo [ClienteDto] en una lista de objetos de tipo [Cliente].
 * @return Una lista de objetos de tipo [Cliente] creada a partir de la lista de clientes DTO.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
fun List<ClienteDto>.toClienteList(): List<Cliente>{
    return map{ it.toCliente() }
}