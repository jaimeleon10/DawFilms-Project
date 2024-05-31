package org.example.dawfilmsinterface.clientes.services

import com.github.michaelbull.result.*
import org.example.dawfilmsinterface.clientes.cache.ClienteCache
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.clientes.repositories.ClienteRepository
import org.example.dawfilmsinterface.clientes.validators.ClienteValidator
import org.example.dawfilmsinterface.productos.errors.ProductoError
import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Implementación de [ClienteService] que gestiona las operaciones relacionadas con los clientes.
 * @param clienteRepository Repositorio de clientes.
 * @param clienteCache Cache de clientes para mejorar el rendimiento.
 * @param clienteValidator Validador de clientes para asegurar la integridad de los datos.
 * @autor Jaime León, German Fernández, Natalia González, Alba García, Javier Ruiz
 * @since 1.0.0
 */
class ClienteServiceImpl(
    private val clienteRepository: ClienteRepository,
    private val clienteCache: ClienteCache,
    private val clienteValidator: ClienteValidator
) : ClienteService {
    override fun getAll(): Result<List<Cliente>, ClienteError> {
        logger.debug { "Obteniendo todos los clientes" }
        val clientes: MutableList<Cliente> = mutableListOf()
        clienteRepository.findAll().forEach { clientes.add(it) }
        return Ok(clientes)
    }

    override fun getById(id: Long): Result<Cliente, ClienteError> {
        logger.debug { "Obteniendo cliente con id: $id" }
        return clienteCache.get(id).mapBoth(
            success = {
                logger.debug { "Cliente encontrado en cache" }
                Ok(it)
            },
            failure = {
                logger.debug { "Cliente no encontrado en cache" }
                clienteRepository.findById(id)
                    ?.let {
                        logger.debug { "Guardando en la cache" }
                        clienteCache.put(id, it)
                        Ok(it)
                    }?: Err(ClienteError.ClienteNoEncontrado("Cliente no encontrado con id: $id"))
            }
        )
    }

    override fun save(cliente: Cliente): Result<Cliente, ClienteError> {
        logger.debug { "Guardando cliente: $cliente" }
        return clienteValidator.validate(cliente).andThen {
                c ->
            logger.debug { "Guardando en cache" }
            clienteCache.put(c.id, c)
            Ok(clienteRepository.save(c))
        }
    }

    override fun update(id: Long, cliente: Cliente): Result<Cliente, ClienteError> {
        logger.debug { "Actualizando cliente con id: $id" }
        return clienteValidator.validate(cliente).andThen { p ->
            clienteRepository.update(id, p)
                ?.let {
                    logger.debug { "Guardando en la cache" }
                    clienteCache.put(id, it)
                    Ok(it)
                }
                ?: Err(ClienteError.ClienteNoActualizado("Cliente no actualizado con id: $id"))
        }
    }

    override fun delete(id: Long): Result<Cliente, ClienteError> {
        logger.debug { "Borrando cliente con id: $id" }
        return clienteRepository.delete(id)
            ?.let {
                logger.debug { "Eliminando de la cache" }
                clienteCache.remove(id)
                Ok(it)
            } ?: Err(ClienteError.ClienteNoEliminado("Butaca no eliminada con id: $id"))
    }

    override fun deleteAllClientes(): Result<Unit, ClienteError> {
        logger.debug { "Borrando todos los clientes" }
        clienteRepository.deleteAll()
        clienteCache.clear()
        return Ok(Unit)
    }

    override fun validateCliente(email: String, encryptedPassword: String): Result<Cliente, ClienteError> {
        logger.debug { "Validando email y password del cliente con email $email" }
        return clienteRepository.validate(email,encryptedPassword)
            ?.let { Ok(it) }
            ?: Err(ClienteError.ClienteValidationError("Login o password incorrectos"))
    }
    override fun getByEmail(email: String): Result<Cliente, ClienteError> {
        logger.debug { "Obteniendo cliente con email $email" }
        return clienteRepository.findByEmail(email)
            ?.let { Ok(it) }
            ?: Err(ClienteError.ClienteValidationError("No existe un cliente con email $email"))
    }
}