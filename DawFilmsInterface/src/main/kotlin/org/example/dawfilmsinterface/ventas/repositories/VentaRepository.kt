package org.example.dawfilmsinterface.ventas.repositories

import com.github.michaelbull.result.Result
import database.VentaEntity
import org.example.dawfilmsinterface.clientes.models.Cliente
import org.example.dawfilmsinterface.ventas.errors.VentaError
import org.example.dawfilmsinterface.ventas.models.LineaVenta
import org.example.dawfilmsinterface.ventas.models.Venta
import java.time.LocalDate
import java.util.*

interface VentaRepository {
    fun findAll(cliente: Cliente, lineas: List<LineaVenta>, fechaCompra: LocalDate): List<Venta>
    fun findById(id: UUID): Venta?
    fun save(venta: Venta): Venta
    fun update(id: UUID, venta: Venta): Venta?
    fun delete (id: UUID): Venta?
    fun validateCliente(cliente: Cliente): Result<Cliente, VentaError>
    fun validateLineas(lineas: List<LineaVenta>): Result<List<LineaVenta>, VentaError>
}