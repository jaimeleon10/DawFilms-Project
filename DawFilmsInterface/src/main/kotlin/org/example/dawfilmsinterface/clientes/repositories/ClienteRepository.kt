package org.example.dawfilmsinterface.clientes.repositories

import org.example.dawfilmsinterface.clientes.models.Cliente

interface ClienteRepository {
    fun findAll(): List<Cliente>
    fun findById(id: Long): Cliente?
    fun save(cliente: Cliente): Cliente
    fun update(id: Long, cliente: Cliente): Cliente?
    fun delete(id: Long): Cliente?
}