package org.example.dawfilmsinterface.clientes.services

import com.github.michaelbull.result.Result
import org.example.dawfilmsinterface.clientes.errors.ClienteError
import org.example.dawfilmsinterface.clientes.models.Cliente

interface ClienteService {
    fun getAll(): Result<List<Cliente>, ClienteError>
    fun getById(id: Long): Result<Cliente, ClienteError>
    fun save(cliente: Cliente): Result<Cliente, ClienteError>
    fun update(id: Long, cliente: Cliente): Result<Cliente, ClienteError>
    fun delete(id: Long): Result<Cliente, ClienteError>
}