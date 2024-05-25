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
                    ?.let { Ok(it) }
                    ?: Err(ClienteError.ClienteNoEncontrado("Cliente no encontrado con id: $id"))
            }
        )
    }

    override fun save(cliente: Cliente): Result<Cliente, ClienteError> {
        logger.debug { "Guardando cliente: $cliente" }
        return clienteValidator.validate(cliente).andThen {
            Ok(clienteRepository.save(it))
        }.andThen { c ->
            println("Guardando en cache")
            clienteCache.put(c.id, c)
        }
    }

    override fun update(id: Long, cliente: Cliente): Result<Cliente, ClienteError> {
        logger.debug { "Actualizando cliente con id: $id" }
        return clienteValidator.validate(cliente).andThen { p ->
            clienteRepository.update(id, p)
                ?.let { Ok(it) }
                ?: Err(ClienteError.ClienteNoActualizado("Cliente no actualizado con id: $id"))
        }.andThen {
            clienteCache.put(id, it)
        }
    }

    override fun delete(id: Long): Result<Cliente, ClienteError> {
        logger.debug { "Borrando cliente con id: $id" }
        return clienteRepository.delete(id)
            ?.let {
                clienteCache.remove(id)
                Ok(it)
            } ?: Err(ClienteError.ClienteNoEliminado("Butaca no eliminada con id: $id"))
    }

    override fun validateCliente(email: String, encryptedPassword: String): Result<Cliente, ClienteError> {
        logger.debug { "Validando email y password del cliente con email $email" }
        return clienteRepository.validate(email,encryptedPassword)
            ?.let { Ok(it) }
            ?: Err(ClienteError.ClienteValidationError("Login o password incorrectos"))
    }
    override fun getByEmail(email: String): Result<Cliente, ClienteError> {
        logger.debug { "Validando email y password del cliente con email $email" }
        return clienteRepository.findByEmail(email)
            ?.let { Ok(it) }
            ?: Err(ClienteError.ClienteValidationError("Login o password incorrectos"))
    }
}