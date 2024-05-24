package org.example.dawfilmsinterface.productos.repositories.butacas

import org.example.dawfilmsinterface.productos.models.butacas.Butaca

interface ButacaRepository {
    fun findAll(): List<Butaca>
    fun findById(id: String): Butaca?
    fun saveAll(butacas: List<Butaca>): List<Butaca>
    fun save (item: Butaca): Butaca
    fun update(id: String, item: Butaca): Butaca?
    fun delete(id: String): Butaca?
    fun deleteAll()
}