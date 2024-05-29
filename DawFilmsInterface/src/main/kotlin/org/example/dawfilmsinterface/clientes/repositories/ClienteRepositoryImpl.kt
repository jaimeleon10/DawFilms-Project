package org.example.dawfilmsinterface.clientes.repositories

import org.example.dawfilmsinterface.clientes.mappers.toCliente
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.database.SqlDeLightManager
import org.lighthousegames.logging.logging
import java.time.LocalDate

private val logger = logging()

/**
 * Clase implementacion del repositorio de clientes
 * @property dbManager implementacion de la base de datos
 * @property db conexion con la base de datos
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
class ClienteRepositoryImpl(
    private val dbManager: SqlDeLightManager
) : ClienteRepository {
    private val db = dbManager.databaseQueries

    override fun findAll(): List<Cliente> {
        logger.debug { "Buscando todos los clientes" }
        return db.selectAllClientes().executeAsList().map { it.toCliente() }
    }

    override fun findById(id: Long): Cliente? {
        logger.debug { "Buscando cliente por id: $id" }
        return db.selectClienteById(id).executeAsOneOrNull()?.toCliente()
    }

    override fun save(cliente: Cliente): Cliente {
        logger.debug { "Guardando cliente: $cliente" }
        val timeStamp = LocalDate.now().toString()
        db.transaction {
            db.insertCliente(
                nombre = cliente.nombre,
                apellido = cliente.apellido,
                fecha_nacimiento = cliente.fechaNacimiento.toString(),
                dni = cliente.dni,
                email = cliente.email,
                numSocio = cliente.numSocio,
                password = cliente.password,
                created_at = timeStamp,
                updated_at = timeStamp,
                is_deleted = 0
            )
        }
        return db.selectClienteLastInserted().executeAsOne().toCliente()
    }

    override fun update(id: Long, cliente: Cliente): Cliente? {
        logger.debug { "Actualizando cliente por id: $id" }
        var result = this.findById(id) ?: return null
        val timeStamp = LocalDate.now()

        result = result.copy(
            nombre = cliente.nombre,
            apellido = cliente.apellido,
            fechaNacimiento = cliente.fechaNacimiento,
            dni = cliente.dni,
            email = cliente.email,
            numSocio = cliente.numSocio,
            password = cliente.password,
            updatedAt = timeStamp,
            isDeleted = cliente.isDeleted
        )

        db.updateCliente(
            nombre = result.nombre,
            apellido = result.apellido,
            fecha_nacimiento = result.fechaNacimiento.toString(),
            dni = result.dni,
            email = result.email,
            numSocio = result.numSocio,
            password = result.password,
            updated_at = timeStamp.toString(),
            is_deleted = if (result.isDeleted) 1 else 0,
            id = id
        )
        return result
    }

    override fun delete(id: Long): Cliente? {
        logger.debug { "Borrando cliente por id: $id" }
        val result = this.findById(id) ?: return null

        val timeStamp = LocalDate.now()
        db.updateCliente(
            nombre = result.nombre,
            apellido = result.apellido,
            fecha_nacimiento = result.fechaNacimiento.toString(),
            dni = result.dni,
            email = result.email,
            numSocio = result.numSocio,
            password = result.password,
            updated_at = timeStamp.toString(),
            is_deleted = 1,
            id = result.id,
        )
        return result.copy(isDeleted = true, updatedAt = timeStamp)
    }

    override fun deleteAll() {
        logger.debug { "Borrando todos los clientes" }
        db.deleteAllClientes()
    }

    override fun validate(email: String, password: String): Cliente? {
        logger.debug { "Validando email y password de cliente con email: $email" }
        return db.selectClienteByEmailAndPassword(email,password).executeAsOneOrNull()?.toCliente()
    }

    override fun findByEmail(email: String): Cliente? {
        logger.debug { "Buscando cliente por email: $email" }
        return db.selectClienteByEmail(email).executeAsOneOrNull()?.toCliente()
    }
}