package org.example.dawfilmsinterface.productos.repositories.complementos

import org.example.dawfilmsinterface.productos.models.complementos.Complemento

interface ComplementoRepository {
    fun findAll(): List<Complemento>
    fun findById(id: String): Complemento?
    fun findByNombre(nombre: String): Complemento?
    fun save (item: Complemento): Complemento
    fun update(id: String, item: Complemento): Complemento?
    fun delete(id: String): Complemento?
    fun deleteAll()
}